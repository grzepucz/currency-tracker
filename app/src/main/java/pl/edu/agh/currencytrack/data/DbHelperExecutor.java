package pl.edu.agh.currencytrack.data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

public class DbHelperExecutor {
    private static final String TAG = DbHelperExecutor.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }
    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static FavouriteCurrency addFavourite(final AppDatabase db, FavouriteCurrency fav) {
        db.currencyDao().insertAll(fav);
        return fav;
    }

    private static void populateWithTestData(AppDatabase db) {
        FavouriteCurrency favouriteCurrency = new FavouriteCurrency("EUR", "Euro", "eur.png", true);
        addFavourite(db, favouriteCurrency);
        favouriteCurrency = new FavouriteCurrency("USD", "Dolar amerykański", "usd.png", true);
        addFavourite(db, favouriteCurrency);
        List<FavouriteCurrency> userList = db.currencyDao().getAll();
        Log.d(DbHelperExecutor.TAG, "Rows Count: " + db.currencyDao().countRows());
        Log.d(DbHelperExecutor.TAG, "Rows Count Name: " + db.currencyDao().findByShortName("EUR").longName);

        for (int i = 0; i < userList.size(); i++) {
            Log.d(DbHelperExecutor.TAG, "Rows Name: " + userList.get(i).longName);
        }
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateWithTestData(mDb);
            return null;
        }
    }
}