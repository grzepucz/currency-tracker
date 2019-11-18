package pl.edu.agh.currencytrack.models;

import java.io.Serializable;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeseriesResponse implements Serializable
{

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("timeseries")
    @Expose
    public Boolean timeseries;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("rates")
    @Expose
    public Map<String, Map<String, Double>>  rates;
    private final static long serialVersionUID = 8354346012557001635L;

}