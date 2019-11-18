package pl.edu.agh.currencytrack.services.providers;

import pl.edu.agh.currencytrack.models.ConvertResponse;
import pl.edu.agh.currencytrack.models.LatestResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ConvertDataProviderApi {
    @POST("convert")
    Call<ConvertResponse> convertFromTo(
            @Query("access_key") String secret,
            @Query("from") String from,
            @Query("to") String to,
            @Query("amount") Double amount
    );

    @POST("convert")
    Call<ConvertResponse> convertFromToWithDate(
            @Query("access_key") String secret,
            @Query("from") String from,
            @Query("to") String to,
            @Query("amount") Double amount,
            @Query("date") String date
    );
}
