package pl.edu.agh.currencytrack.controllers;

import androidx.recyclerview.widget.RecyclerView;

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
public class LatestController extends Controller {
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
        call.enqueue(new Callback<LatestResponse>() {
            @Override
            public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {

            }

            @Override
            public void onFailure(Call<LatestResponse> call, Throwable t) {

            }
        });
    }

    public void processLatestWithBaseRequest(String base) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LatestDataProviderAPI fixer = retrofit.create(LatestDataProviderAPI.class);

        Call<LatestResponse> call = fixer.getLatestWithBase(this.secret, base);
        call.enqueue(new Callback<LatestResponse>() {
            @Override
            public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {

            }

            @Override
            public void onFailure(Call<LatestResponse> call, Throwable t) {

            }
        });
    }

    public void processLatestWithSymbolsRequest(String symbols) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LatestDataProviderAPI fixer = retrofit.create(LatestDataProviderAPI.class);

        Call<LatestResponse> call = fixer.getLatestWithSymbols(this.secret, symbols);
        call.enqueue(new Callback<LatestResponse>() {
            @Override
            public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {

            }

            @Override
            public void onFailure(Call<LatestResponse> call, Throwable t) {

            }
        });
    }

    public void processLatestWithBaseAndSymbolsRequest(String base, String symbols) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LatestDataProviderAPI fixer = retrofit.create(LatestDataProviderAPI.class);

        Call<LatestResponse> call = fixer.getLatestWithBaseAndSymbols(this.secret, base, symbols);
        call.enqueue(new Callback<LatestResponse>() {

            @Override
            public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {
                if(response.isSuccessful()) {
                    latest = (LatestResponse) response.body();
                    System.out.println(latest.date);
                    System.out.println(latest.base);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<LatestResponse> call, Throwable t) {

            }
        });
    }

    public void loadDataToView(LatestResponse response) {

    }

    public LatestResponse getLatest() {
        return latest;
    }
}