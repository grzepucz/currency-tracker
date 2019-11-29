package pl.edu.agh.currencytrack.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.data.NotificationLimit;

@Dao
public interface NotificationLimitDAO {
    @Query("SELECT * FROM notification")
    List<NotificationLimit> getAll();

    @Query("SELECT * FROM notification WHERE short_name IN (:shortNames)")
    List<NotificationLimit> getByShortNames(List<String> shortNames);

    @Query("SELECT * FROM notification WHERE short_name = :shortName")
    NotificationLimit getByShortName(String shortName);

    @Query("SELECT * FROM notification WHERE notifyId = :id")
    NotificationLimit getById(int id);

    @Query("SELECT * FROM notification WHERE notifyId IN (:ids)")
    List<NotificationLimit> getByIds(List<Integer> ids);

    @Insert
    void insertAll(NotificationLimit... limits);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NotificationLimit limit);

    @Delete
    void delete(NotificationLimit limit);

    @Query("DELETE FROM notification")
    void deleteAll();

    @Query("UPDATE notification SET `limit` = :limit, `to_currency` = :toCurrency, `notify` = :notify WHERE notifyId = :id")
    void updateLimitById(int id, String toCurrency, Double limit, Boolean notify);

    @Query("UPDATE notification SET `limit` = :limit, `to_currency` = :toCurrency, `notify` = :notify  WHERE short_name = :name")
    void updateLimitByName(String name, String toCurrency, Double limit, Boolean notify);

    @Update
    void updateAll(NotificationLimit... limits);

    @Query("SELECT COUNT(*) from notification")
    int countRows();
}
