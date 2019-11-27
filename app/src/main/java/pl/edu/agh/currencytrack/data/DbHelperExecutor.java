package pl.edu.agh.currencytrack.data;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class DbHelperExecutor {
    private static final String TAG = DbHelperExecutor.class.getName();

    public static List<FavouriteCurrency> getAllFavouritesAsync(@NonNull final AppDatabase db) {
        try {
            GetAllFavouritesDbAsync task = new GetAllFavouritesDbAsync(db);
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<NotificationLimit> getAllNotificationsAsync(@NonNull final AppDatabase db) {
        try {
            GetAllNotificationDbAsync task = new GetAllNotificationDbAsync(db);
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static FavouriteCurrency getFavouriteByShortAsync(@NonNull final AppDatabase db, String shortName) {
        try {
            GetFavouriteByShortDbAsync task = new GetFavouriteByShortDbAsync(db, shortName);
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<NotificationLimit> getNotificationsByCodesAsync(@NonNull final AppDatabase db, @NonNull List<String> names) {
        try {
            GetByCodesNotificationDbAsync task = new GetByCodesNotificationDbAsync(db, names);
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<NotificationLimit> getNotificationsByIdsAsync(@NonNull final AppDatabase db, @NonNull List<Integer> ids) {
        try {
            GetByNotifyIdNotificationDbAsync task = new GetByNotifyIdNotificationDbAsync(db, ids);
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<NotificationLimit> getNotificationAsync(@NonNull final AppDatabase db, @NonNull List<String> names) {
        try {
            GetByCodesNotificationDbAsync task = new GetByCodesNotificationDbAsync(db, names);
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
        UpdateFavouriteDbAsync task = new UpdateFavouriteDbAsync(db, names);
        task.execute();
    }

    public static void updateNotificationElementAsync(@NonNull final AppDatabase db, @NonNull List<NotificationLimit> notifications) {
        UpdateNotificationDbAsync task = new UpdateNotificationDbAsync(db, notifications);
        task.execute();
    }

    public static void insertNotificationElementAsync(@NonNull final AppDatabase db, @NonNull List<NotificationLimit> notifications) {
        InsertNotificationDbAsync task = new InsertNotificationDbAsync(db, notifications);
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

    private static List<FavouriteCurrency> getAllFavouritesData(AppDatabase db) {
        return db.currencyDao().getAll();
    }

    private static FavouriteCurrency getFavouriteByShortName(AppDatabase db, String name) {
        return db.currencyDao().findByShortName(name);
    }

    private static List<FavouriteCurrency> getAllObservedData(AppDatabase db) {
        return db.currencyDao().getAllObserved();
    }

    private static void insertFavouriteData(AppDatabase db, FavouriteCurrency... currencies) {
        db.currencyDao().insertAll(currencies);
    }

    private static void deleteFavouriteData(AppDatabase db, FavouriteCurrency currency) {
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

    private static class GetAllFavouritesDbAsync extends AsyncTask<Void, Void, List<FavouriteCurrency>> {
        private final AppDatabase mDb;

        GetAllFavouritesDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected List<FavouriteCurrency> doInBackground(Void... voids) {
            return getAllFavouritesData(mDb);
        }

        @Override
        protected void onPostExecute(List<FavouriteCurrency> result) {
            super.onPostExecute(result);
        }
    }

    private static class GetFavouriteByShortDbAsync extends AsyncTask<String, Void, FavouriteCurrency> {
        private final AppDatabase mDb;
        private final String shortName;

        GetFavouriteByShortDbAsync(AppDatabase db, String shortName) {
            this.mDb = db;
            this.shortName = shortName;
        }

        @Override
        protected FavouriteCurrency doInBackground(String... strings) {
            return getFavouriteByShortName(mDb, shortName);
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

    private static class InsertFavouriteDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase mDb;
        private FavouriteCurrency mCurrency;

        InsertFavouriteDbAsync(AppDatabase db, FavouriteCurrency currency ) {
            mDb = db;
            mCurrency = currency;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            insertFavouriteData(mDb, mCurrency);
            return null;
        }
    }

    private static class UpdateFavouriteDbAsync extends AsyncTask<List<String>, Void, Void> {
        private final AppDatabase mDb;
        private List<String> mCurrency;

        UpdateFavouriteDbAsync(AppDatabase db, List<String> currency) {
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

    private static class DeleteFavouriteDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase mDb;
        private FavouriteCurrency mCurrency;

        DeleteFavouriteDbAsync(AppDatabase db, FavouriteCurrency currency) {
            mDb = db;
            mCurrency = currency;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            deleteFavouriteData(mDb, mCurrency);
            return null;
        }
    }

/*
    Notification fragmnt
 */

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void updateNotificationData(AppDatabase db, List<NotificationLimit> elems) {

        elems = elems.stream()
                .map(i -> {
                    db.notificationLimitDao().updateLimitById(i.getNotifyId(), i.getToCurrency(), i.getLimit(), i.getShouldNotify());
                    return i;
                })
                .collect(Collectors.toList());

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void insertNotificationData(AppDatabase db, List<NotificationLimit> elems) {

        elems = elems.stream()
                .map(i -> {
                    db.notificationLimitDao().insert(i);
                    return i;
                })
                .collect(Collectors.toList());

    }

    private static List<NotificationLimit> getAllNotificationData(AppDatabase db) {
        return db.notificationLimitDao().getAll();
    }

    private static List<NotificationLimit>  getByCodesNotificationData(AppDatabase db, List<String> codes) {
        return db.notificationLimitDao().getByShortNames(codes);
    }

    private static List<NotificationLimit>  getByIdsNotificationData(AppDatabase db, List<Integer> ids) {
        return db.notificationLimitDao().getByIds(ids);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void deleteNotificationData(AppDatabase db, List<String> codes) {
        db.notificationLimitDao().getByShortNames(codes).forEach(i -> db.notificationLimitDao().delete(i));
    }

    private static class InsertNotificationDbAsync extends AsyncTask<List<NotificationLimit>, Void, Void> {
        private final AppDatabase mDb;
        private List<NotificationLimit> mNotifications;

        InsertNotificationDbAsync(AppDatabase db, List<NotificationLimit> notificationLimits) {
            mDb = db;
            mNotifications = notificationLimits;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(List<NotificationLimit>... lists) {
            insertNotificationData(mDb, mNotifications);
            return null;
        }
    }

    private static class DeleteNotificationDbAsync extends AsyncTask<List<String>, Void, Void> {
        private final AppDatabase mDb;
        private List<String> mNames;

        DeleteNotificationDbAsync(AppDatabase db, List<String> names) {
            mDb = db;
            mNames = names;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(List<String>... names) {
            deleteNotificationData(mDb, mNames);
            return null;
        }
    }


    private static class GetAllNotificationDbAsync extends AsyncTask<Void, Void, List<NotificationLimit>> {
        private final AppDatabase mDb;

        GetAllNotificationDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected List<NotificationLimit> doInBackground(Void... voids) {
            return getAllNotificationData(mDb);
        }
    }

    private static class GetByCodesNotificationDbAsync extends AsyncTask<List<String>, Void, List<NotificationLimit>> {
        private final AppDatabase mDb;
        private List<String> mCodes;

        GetByCodesNotificationDbAsync(AppDatabase db, List<String> codes) {
            mDb = db;
            mCodes = codes;
        }

        @Override
        protected List<NotificationLimit> doInBackground(List<String>... lists) {
            return getByCodesNotificationData(mDb, mCodes);
        }
    }

    private static class GetByNotifyIdNotificationDbAsync extends AsyncTask<List<Integer>, Void, List<NotificationLimit>> {
        private final AppDatabase mDb;
        private List<Integer> mIds;

        GetByNotifyIdNotificationDbAsync(AppDatabase db, List<Integer> ids) {
            mDb = db;
            mIds = ids;
        }

        @Override
        protected List<NotificationLimit> doInBackground(List<Integer>... lists) {
            return getByIdsNotificationData(mDb, mIds);
        }
    }



    private static class UpdateNotificationDbAsync extends AsyncTask<List<NotificationLimit>, Void, Void> {
        private final AppDatabase mDb;
        private List<NotificationLimit> mNotification;

        UpdateNotificationDbAsync(AppDatabase db, List<NotificationLimit> notification) {
            mDb = db;
            mNotification = notification;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(List<NotificationLimit>... notifications) {
            updateNotificationData(mDb, mNotification);
            return null;
        }
    }
}