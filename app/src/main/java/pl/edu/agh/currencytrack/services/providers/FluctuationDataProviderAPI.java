package pl.edu.agh.currencytrack.services.providers;

import pl.edu.agh.currencytrack.models.FluctuationResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FluctuationDataProviderAPI {
    @POST("fluctuation")
    Call<FluctuationResponse> getFluctuationFromTo(
            @Query("access_key") String secret,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate
    );

    @POST("fluctuation")
    Call<FluctuationResponse> getFluctuationFromToWithBase(
            @Query("access_key") String secret,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("base") String base
    );

    @POST("fluctuation")
    Call<FluctuationResponse> getFluctuationFromToWithSymbols(
            @Query("access_key") String secret,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("symbols") String symbols
    );

    @POST("fluctuation")
    Call<FluctuationResponse> getFluctuationFromToWithBaseAndSymbols(
            @Query("access_key") String secret,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate,
            @Query("base") String base,
            @Query("symbols") String symbols
    );
}
