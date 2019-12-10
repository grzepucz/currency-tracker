package pl.edu.agh.currencytrack;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.edu.agh.currencytrack.models.ConvertResponse;
import pl.edu.agh.currencytrack.models.HistoricalResponse;
import pl.edu.agh.currencytrack.models.LatestResponse;
import pl.edu.agh.currencytrack.services.providers.ConvertDataProviderAPI;
import pl.edu.agh.currencytrack.services.providers.HistoricalDataProviderAPI;
import pl.edu.agh.currencytrack.services.providers.LatestDataProviderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ApiResponseTest {

    @Test
    public void getConvertResponse() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ConvertDataProviderAPI fixer = retrofit.create(ConvertDataProviderAPI.class);

        Call<ConvertResponse> call = fixer.convertFromTo(
                BuildConfig.API_SECRET,
                "EUR",
                "PLN",
                1.0
        );

        call.enqueue(new Callback<ConvertResponse>() {
            @Override
            public void onResponse(Call<ConvertResponse> call, Response<ConvertResponse> response) {
                if(response.isSuccessful() && response.body().success) {
                    assertNotNull(response.body().date);
                    assertNotNull(response.body().info);
                    assertNotNull(response.body().query);
                    assertNotNull(response.body().result);
                    assertNotNull(response.body().historical);
                } else {
                    fail();
                }
            }

            @Override
            public void onFailure(Call<ConvertResponse> call, Throwable t) {
                fail();
            }
        });
    }

    @Test
    public void getLatestResponse() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LatestDataProviderAPI fixer = retrofit.create(LatestDataProviderAPI.class);

        Call<LatestResponse> call = fixer.getLatest(
                BuildConfig.API_SECRET
        );

        call.enqueue(new Callback<LatestResponse>() {
            @Override
            public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {
                if(response.isSuccessful() && response.body().success) {
                    assertNotNull(response.body().date);
                    assertNotNull(response.body().base);
                    assertNotNull(response.body().timestamp);
                    assertNotNull(response.body().rates);
                } else {
                    fail();
                }
            }

            @Override
            public void onFailure(Call<LatestResponse> call, Throwable t) {
                fail();
            }
        });
    }

    @Test
    public void getHistoricalResponse() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        HistoricalDataProviderAPI fixer = retrofit.create(HistoricalDataProviderAPI.class);

        Call<HistoricalResponse> call = fixer.getHistorical(
                "2017-10-26",
                BuildConfig.API_SECRET
        );

        call.enqueue(new Callback<HistoricalResponse>() {
            @Override
            public void onResponse(Call<HistoricalResponse> call, Response<HistoricalResponse> response) {
                if(response.isSuccessful() && response.body().success) {
                    assertNotNull(response.body().date);
                    assertNotNull(response.body().base);
                    assertNotNull(response.body().historical);
                    assertNotNull(response.body().rates);
                    assertNotNull(response.body().timestamp);
                } else {
                    fail();
                }
            }

            @Override
            public void onFailure(Call<HistoricalResponse> call, Throwable t) {
                fail();
            }
        });
    }
}
