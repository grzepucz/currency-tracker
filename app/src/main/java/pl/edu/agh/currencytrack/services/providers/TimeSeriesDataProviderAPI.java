package pl.edu.agh.currencytrack.services.providers;

import pl.edu.agh.currencytrack.models.TimeseriesResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TimeSeriesDataProviderAPI {
    @POST("timeseries")
    Call<TimeseriesResponse> getTimeseriesFromTo(
            @Query("access_key") String secret,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate
    );

    @POST("timeseries")
    Call<TimeseriesResponse> getTimeseriesFromToWithBase(
            @Query("access_key") String secret,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("base") String base
    );

    @POST("timeseries")
    Call<TimeseriesResponse> getTimeseriesFromToWithSymbols(
            @Query("access_key") String secret,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("symbols") String symbols
    );

    @POST("timeseries")
    Call<TimeseriesResponse> getTimeseriesFromToWithBaseAndSymbols(
            @Query("access_key") String secret,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("base") String base,
            @Query("symbols") String symbols
    );
}
