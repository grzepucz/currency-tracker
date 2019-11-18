package pl.edu.agh.currencytrack.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConvertResponse implements Serializable
{

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("query")
    @Expose
    public Query query;
    @SerializedName("info")
    @Expose
    public Info info;
    @SerializedName("historical")
    @Expose
    public String historical;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("result")
    @Expose
    public Double result;
    private final static long serialVersionUID = -2700348385598090419L;

    public class Info implements Serializable
    {

        @SerializedName("timestamp")
        @Expose
        public String timestamp;
        @SerializedName("rate")
        @Expose
        public Double rate;
        private final static long serialVersionUID = -6537463075824205019L;

    }

    public class Query implements Serializable
    {

        @SerializedName("from")
        @Expose
        public String from;
        @SerializedName("to")
        @Expose
        public String to;
        @SerializedName("amount")
        @Expose
        public Double amount;
        private final static long serialVersionUID = -7710621590462495080L;

    }
}