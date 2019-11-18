package pl.edu.agh.currencytrack.services.providers;

import pl.edu.agh.currencytrack.models.LatestResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LatestDataProviderAPI {
    @POST("latest")
    Call<LatestResponse> getLatest(@Query("access_key") String secret);

    @POST("latest")
    Call<LatestResponse> getLatestWithBase(@Query("access_key") String secret, @Query("base") String base);

    @POST("latest")
    Call<LatestResponse> getLatestWithSymbols(@Query("access_key") String secret, @Query("symbols") String symbols);

    @POST("latest")
    Call<LatestResponse> getLatestWithBaseAndSymbols(
            @Query("access_key") String secret,
            @Query("base") String base,
            @Query("symbols") String symbols
    );
}
