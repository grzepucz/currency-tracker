package pl.edu.agh.currencytrack.models;

import java.io.Serializable;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FluctuationResponse implements Serializable
{

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("fluctuation")
    @Expose
    public Boolean fluctuation;
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
    public Map<String, Rates> rates;
    private final static long serialVersionUID = -3149987540750181361L;

    public class Rates implements Serializable
    {

        @SerializedName("start_rate")
        @Expose
        public Double startRate;
        @SerializedName("end_rate")
        @Expose
        public Double endRate;
        @SerializedName("change")
        @Expose
        public Double change;
        @SerializedName("change_pct")
        @Expose
        public Double changePct;
        private final static long serialVersionUID = -8321848731737785261L;

    }
}