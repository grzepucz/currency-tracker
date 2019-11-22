package pl.edu.agh.currencytrack.data;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import pl.edu.agh.currencytrack.data.DAO.FavouritesCurrencyDAO;

public class DbHelperExecutor {
    private static final String TAG = DbHelperExecutor.class.getName();

    public static List<FavouriteCurrency> getAllAsync(@NonNull final AppDatabase db) {
        try {
            GetAllDbAsync task = new GetAllDbAsync(db);
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<FavouriteCurrency> getAllObservedAsync(@NonNull final AppDatabase db) {
        try {
            GetAllObservedDbAsync task = new GetAllObservedDbAsync(db);
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void populateInitAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void updateElementListObservationAsync(@NonNull final AppDatabase db, @NonNull List<String> names) {
        UpdateDbAsync task = new UpdateDbAsync(db, names);
        task.execute();
    }

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static void populateWithTestData(AppDatabase db) {
        Log.d(DbHelperExecutor.TAG, "Rows Count, " + db.currencyDao().countRows());

        List<FavouriteCurrency> userList = db.currencyDao().getAllObserved();
        for (int i = 0; i < userList.size(); i++) {
            Log.d(DbHelperExecutor.TAG, "Rows Name, " + userList.get(i).getLongName());
        }
    }

    private static List<FavouriteCurrency> getAllData(AppDatabase db) {
        return db.currencyDao().getAll();
    }

    private static List<FavouriteCurrency> getAllObservedData(AppDatabase db) {
        return db.currencyDao().getAllObserved();
    }

    private static void insertData(AppDatabase db, FavouriteCurrency... currencies) {
        db.currencyDao().insertAll(currencies);
    }

    private static void deleteData(AppDatabase db, FavouriteCurrency currency) {
        db.currencyDao().delete(currency);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void updateObservableItems(AppDatabase db, List<String> currencies) {

        List<FavouriteCurrency> elems = db.currencyDao().loadAllByCode(currencies);

        elems = elems.stream()
                .map(i -> {
                    db.currencyDao().updateObservable(i.getUid(), i.getObserved() ? 0 : 1);
                    return i;
                })
                .collect(Collectors.toList());

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

    private static class GetAllDbAsync extends AsyncTask<Void, Void, List<FavouriteCurrency>> {
        private final AppDatabase mDb;

        GetAllDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected List<FavouriteCurrency> doInBackground(Void... voids) {
            return getAllData(mDb);
        }

        @Override
        protected void onPostExecute(List<FavouriteCurrency> result) {
            super.onPostExecute(result);
        }
    }

    private static class GetAllObservedDbAsync extends AsyncTask<Void, Void, List<FavouriteCurrency>> {
        private final AppDatabase mDb;

        GetAllObservedDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected List<FavouriteCurrency> doInBackground(Void... voids) {
            return getAllObservedData(mDb);
        }
    }

    private static class InsertDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase mDb;
        private FavouriteCurrency mCurrency;

        InsertDbAsync(AppDatabase db, FavouriteCurrency currency ) {
            mDb = db;
            mCurrency = currency;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            insertData(mDb, mCurrency);
            return null;
        }
    }

    private static class UpdateDbAsync extends AsyncTask<List<String>, Void, Void> {
        private final AppDatabase mDb;
        private List<String> mCurrency;

        UpdateDbAsync(AppDatabase db, List<String> currency) {
            mDb = db;
            mCurrency = currency;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(List<String>... currency) {
            updateObservableItems(mDb, mCurrency);
            return null;
        }
    }

    private static class DeleteDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase mDb;
        private FavouriteCurrency mCurrency;

        DeleteDbAsync(AppDatabase db, FavouriteCurrency currency) {
            mDb = db;
            mCurrency = currency;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            deleteData(mDb, mCurrency);
            return null;
        }
    }
}