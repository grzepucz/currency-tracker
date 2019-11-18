package pl.edu.agh.currencytrack.services.providers;

import pl.edu.agh.currencytrack.models.HistoricalResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HistoricalDataProviderAPI {
    @POST("{historical}")
    Call<HistoricalResponse> getHistorical(@Path("historical") String historical, @Query("access_key") String secret);

    @POST("{historical}")
    Call<HistoricalResponse> getHistoricalWithBase(
            @Path("historical") String historical,
            @Query("access_key") String secret,
            @Query("base") String base
    );

    @POST("{historical}")
    Call<HistoricalResponse> getHistoricalWithSymbols(
            @Path("historical") String historical,
            @Query("access_key") String secret,
            @Query("symbols") String symbols
    );

    @POST("{historical}")
    Call<HistoricalResponse> getHistoricalWithBaseAndSymbols(
            @Path("historical") String historical,
            @Query("access_key") String secret,
            @Query("base") String base,
            @Query("symbols") String symbols
    );

}
