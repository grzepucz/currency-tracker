package pl.edu.agh.currencytrack.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pl.edu.agh.currencytrack.data.Currency;

@Dao
public interface CurrencyDAO {
    @Query("SELECT * FROM currency")
    LiveData<List<Currency>> getAll();

    @Query("SELECT * FROM currency WHERE uid IN (:currenciesIds)")
    LiveData<List<Currency>> loadAllByIds(int[] currenciesIds);

    @Query("SELECT * FROM currency WHERE short_name LIKE :shortName LIMIT 1")
    Currency findByShortName(String shortName);

    @Insert
    void insertAll(Currency... currencies);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Currency currency);

    @Delete
    void delete(Currency currency);

    @Query("DELETE FROM currency")
    void deleteAll();
}
