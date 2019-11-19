package pl.edu.agh.currencytrack.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;

@Entity(tableName = "favourites")
public class FavouriteCurrency {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "short_name")
    public String shortName;

    @ColumnInfo(name = "long_name")
    public String longName;

    @ColumnInfo(name = "icon")
    public String icon;

    @ColumnInfo(name = "observed")
    public Boolean isObserved;

    public FavouriteCurrency(int uid, String shortName, String longName, String icon, Boolean isObserved) {
        this.uid = uid;
        this.shortName = shortName;
        this.longName = longName;
        this.icon = icon;
        this.isObserved = isObserved;
    }
    @Ignore
    public FavouriteCurrency(String shortName, String longName, String icon, Boolean isObserved) {
        this.shortName = shortName;
        this.longName = longName;
        this.icon = icon;
        this.isObserved = isObserved;
    }
}
