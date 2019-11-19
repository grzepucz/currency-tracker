package pl.edu.agh.currencytrack.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.edu.agh.currencytrack.data.DAO.FavouritesCurrencyDAO;

@Database(entities = {FavouriteCurrency.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavouritesCurrencyDAO currencyDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "favourites")
                            .addCallback(sAppDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static AppDatabase.Callback sAppDatabaseCallback = new AppDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
//                FavouritesCurrencyDAO dao = INSTANCE.currencyDao();
//
//                System.out.println("beeee");
//
//                FavouriteCurrency favouriteCurrency = new FavouriteCurrency("USD", "Dolar amerykański", "usd.png", false);
//                dao.insert(favouriteCurrency);
//                favouriteCurrency = new FavouriteCurrency("EUR", "Euro", "eur.png", true);
//                dao.insert(favouriteCurrency);
//
//                System.out.println(favouriteCurrency.longName);
            });
        }
    };
}