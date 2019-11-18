
package pl.edu.agh.currencytrack.models;

import java.io.Serializable;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoricalResponse implements Serializable
{
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("historical")
    @Expose
    public Boolean historical;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("timestamp")
    @Expose
    public Integer timestamp;
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("rates")
    @Expose
    public Map<String, Double> rates;
    private final static long serialVersionUID = -6730415008856514992L;
}