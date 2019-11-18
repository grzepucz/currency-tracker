package pl.edu.agh.currencytrack.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import pl.edu.agh.currencytrack.models.LatestResponse;
import pl.edu.agh.currencytrack.services.providers.LatestDataProviderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Getter
public class LatestController extends Controller implements Callback<LatestResponse> {
    private LatestResponse latest;

    public void processLatestRequest() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LatestDataProviderAPI fixer = retrofit.create(LatestDataProviderAPI.class);

        Call<LatestResponse> call = fixer.getLatest(this.secret);
        call.enqueue(this);
    }

    public void processLatestWithSymbolsRequest() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LatestDataProviderAPI fixer = retrofit.create(LatestDataProviderAPI.class);

        Call<LatestResponse> call = fixer.getLatestWithSymbols(this.secret, "USD,PLN,EUR,AUD");
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {
        if(response.isSuccessful()) {
            LatestResponse latest = response.body();
            System.out.println(latest.rates.size());
            System.out.println(latest.rates.get("USD"));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<LatestResponse> call, Throwable t) {
        t.printStackTrace();
    }

    public LatestResponse getLatest() {
        return latest;
    }
}