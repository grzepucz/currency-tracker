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

@Dao
public interface FavouritesCurrencyDAO {
    @Query("SELECT * FROM favourites")
    List<FavouriteCurrency> getAll();

    @Query("SELECT * FROM favourites WHERE observed = 'true'")
    List<FavouriteCurrency> getAllObserved();

    @Query("SELECT * FROM favourites WHERE uid IN (:currenciesIds)")
    List<FavouriteCurrency> loadAllByIds(int[] currenciesIds);

    @Query("SELECT * FROM favourites WHERE short_name LIKE :shortName LIMIT 1")
    FavouriteCurrency findByShortName(String shortName);

    @Insert
    void insertAll(FavouriteCurrency... currencies);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavouriteCurrency favouriteCurrency);

    @Delete
    void delete(FavouriteCurrency favouriteCurrency);

    @Query("DELETE FROM favourites")
    void deleteAll();

    @Update
    void update(FavouriteCurrency favouriteCurrency);

    @Query("SELECT COUNT(*) from favourites")
    int countRows();
}
