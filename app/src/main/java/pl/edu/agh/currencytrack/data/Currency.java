package pl.edu.agh.currencytrack.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;

@Entity(tableName = "currency")
public class Currency {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "short_name")
    public String shortName;

    @ColumnInfo(name = "long_name")
    public String longName;

    @ColumnInfo(name = "icon")
    public String icon;

    public Currency(int uid, String shortName, String longName, String icon) {
        this.uid = uid;
        this.shortName = shortName;
        this.longName = longName;
        this.icon = icon;
    }
    @Ignore
    public Currency(String shortName, String longName, String icon) {
        this.shortName = shortName;
        this.longName = longName;
        this.icon = icon;
    }
}
