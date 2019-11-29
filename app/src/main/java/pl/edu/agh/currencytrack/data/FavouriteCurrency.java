package pl.edu.agh.currencytrack.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourites")
public class FavouriteCurrency {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "short_name")
    private String shortName;

    @ColumnInfo(name = "long_name")
    private String longName;

    @ColumnInfo(name = "icon")
    private String icon;

    @ColumnInfo(name = "observed")
    private Boolean isObserved;

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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getObserved() {
        return isObserved;
    }

    public void setObserved(Boolean observed) {
        isObserved = observed;
    }

    public FavouriteCurrency changeObservable() {
        setObserved(!isObserved);
        return this;
    }

}
