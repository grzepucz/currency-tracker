package pl.edu.agh.currencytrack;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.DbHelperExecutor;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.data.NotificationLimit;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    @Before
    public void addToDatabase() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        FavouriteCurrency newElem = new FavouriteCurrency("TEST", "TEST", "TEST", false);
        NotificationLimit newElem2 = new NotificationLimit("TEST2", "TEST2", 1.42, true);

        List<NotificationLimit> notificationsList = new ArrayList<>();
        notificationsList.add(newElem2);

        DbHelperExecutor.insertFavouriteDbAsync(AppDatabase.getDatabase(appContext), newElem);
        DbHelperExecutor.insertNotificationElementAsync(AppDatabase.getDatabase(appContext), notificationsList);
    }

    @Test
    public void getFavouriteByName() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        FavouriteCurrency created = new FavouriteCurrency("TEST", "TEST", "TEST", false);
        FavouriteCurrency fc = DbHelperExecutor.getFavouriteByShortAsync(AppDatabase.getDatabase(appContext), "TEST");

        assertEquals(created.getObserved(), fc.getObserved());
        assertEquals(created.getIcon(), fc.getIcon());
        assertEquals(created.getLongName(), fc.getLongName());
        assertEquals(created.getShortName(), fc.getShortName());
    }

    @Test
    public void getNotificationByName() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        NotificationLimit elem = new NotificationLimit("TEST2", "TEST2", 1.42, true);
        List<String> codes = new ArrayList<>();
        codes.add("TEST2");

        List<NotificationLimit> nl = DbHelperExecutor.getNotificationsByCodesAsync(AppDatabase.getDatabase(appContext), codes);

        assertEquals(nl.get(0).getLimit(), elem.getLimit());
        assertEquals(nl.get(0).getShortName(), elem.getShortName());
        assertEquals(nl.get(0).getShouldNotify(), elem.getShouldNotify());
        assertEquals(nl.get(0).getToCurrency(), elem.getToCurrency());
    }

    @Test
    public void updateNotification() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        List<String> codes = new ArrayList<>();
        codes.add("TEST2");

        NotificationLimit elem = DbHelperExecutor.getNotificationsByCodesAsync(AppDatabase.getDatabase(appContext), codes).get(0);
        List<NotificationLimit> nlList = new ArrayList<>();
        elem.setToCurrency("EDITED");
        elem.setLimit(1.23);
        nlList.add(elem);

        DbHelperExecutor.updateNotificationElementAsync(AppDatabase.getDatabase(appContext), nlList);

        List<NotificationLimit> nl = DbHelperExecutor.getNotificationsByCodesAsync(AppDatabase.getDatabase(appContext), codes);

        assertEquals(nl.get(0).getLimit(), elem.getLimit());
        assertEquals(nl.get(0).getShortName(), elem.getShortName());
        assertEquals(nl.get(0).getShouldNotify(), elem.getShouldNotify());
        assertEquals(nl.get(0).getToCurrency(), elem.getToCurrency());
    }

    @Test
    public void updateFavourite() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        List<String> list = new ArrayList<>();
        list.add("TEST");

        DbHelperExecutor.updateElementListObservationAsync(AppDatabase.getDatabase(appContext), list);
        FavouriteCurrency updated = DbHelperExecutor.getFavouriteByShortAsync(AppDatabase.getDatabase(appContext), "TEST");

        assertEquals(updated.getObserved(), true);
    }

    @Test
    public void deleteNotification() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        List<String> list = new ArrayList<>();
        list.add("TEST2");

        DbHelperExecutor.deleteNotificationsAsync(AppDatabase.getDatabase(appContext), list);

        assertEquals(DbHelperExecutor.getNotificationsByCodesAsync(AppDatabase.getDatabase(appContext), list), new ArrayList<NotificationLimit>());
    }

    @After
    public void cleanUpDB() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        List<String> list = new ArrayList<>();
        list.add("TEST");
        list.add("TEST2");
        DbHelperExecutor.deleteNotificationsAsync(AppDatabase.getDatabase(appContext), list);
        DbHelperExecutor.deleteFavouriteByShortAsync(AppDatabase.getDatabase(appContext), "TEST");
    }
}
