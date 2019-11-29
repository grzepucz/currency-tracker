package pl.edu.agh.currencytrack.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

/**
 *
 */
@Entity(tableName="notification",
        foreignKeys = @ForeignKey(entity = FavouriteCurrency.class,
        parentColumns = "uid",
        childColumns = "notifyId",
        onDelete = CASCADE))
public class NotificationLimit {
    @PrimaryKey(autoGenerate = true)
    private int notifyId;

    @ColumnInfo(name = "short_name")
    private String shortName;

    @ColumnInfo(name = "to_currency")
    private String toCurrency;

    @ColumnInfo(name = "limit")
    private Double limit;

    @ColumnInfo(name = "notify")
    private Boolean shouldNotify;

    public NotificationLimit(int notifyId, String shortName, String toCurrency, Double limit, Boolean shouldNotify) {
        this.notifyId = notifyId;
        this.shortName = shortName;
        this.toCurrency = toCurrency;
        this.limit = limit;
        this.shouldNotify = shouldNotify;
    }

    @Ignore
    public NotificationLimit(String shortName, String toCurrency, Double limit, Boolean shouldNotify) {
        this.shortName = shortName;
        this.toCurrency = toCurrency;
        this.limit = limit;
        this.shouldNotify = shouldNotify;
    }

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int uid) {
        this.notifyId = uid;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Boolean getShouldNotify() {
        return shouldNotify;
    }

    public void setShouldNotify(Boolean shouldNotify) {
        this.shouldNotify = shouldNotify;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }
}
