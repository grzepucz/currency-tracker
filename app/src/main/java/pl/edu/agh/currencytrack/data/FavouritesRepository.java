package pl.edu.agh.currencytrack.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import pl.edu.agh.currencytrack.data.DAO.CurrencyDAO;

public class FavouritesRepository {
    private CurrencyDAO mCurrencyDao;
    private LiveData<List<Currency>> mAllCurrencies;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    FavouritesRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mCurrencyDao = db.currencyDao();
        mAllCurrencies = mCurrencyDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Currency>> getAllFavourites() {
        return mAllCurrencies;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Currency currency) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCurrencyDao.insert(currency);
        });
    }
}
