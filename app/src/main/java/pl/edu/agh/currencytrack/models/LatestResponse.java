package pl.edu.agh.currencytrack.models;

import java.io.Serializable;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Generated
public class LatestResponse extends RequestResponse implements Serializable
{
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("timestamp")
    @Expose
    public String timestamp;
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("rates")
    @Expose
    public Map<String, Double> rates;
    private final static long serialVersionUID = 4709408422891834909L;
}
