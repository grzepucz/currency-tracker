package pl.edu.agh.currencytrack.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import pl.edu.agh.currencytrack.models.HistoricalResponse;
import pl.edu.agh.currencytrack.services.providers.HistoricalDataProviderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Getter
public class HistoricalController extends Controller implements Callback<HistoricalResponse> {
    private HistoricalResponse historical;

    public void processHistoricalRequest() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        HistoricalDataProviderAPI fixer = retrofit.create(HistoricalDataProviderAPI.class);

//        Call<HistoricalResponse> call = fixer.getLatest(this.secret);
//        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<HistoricalResponse> call, Response<HistoricalResponse> response) {
        if(response.isSuccessful()) {
            HistoricalResponse latest = response.body();
//            System.out.println(latest.rates.size());
//            System.out.println(latest.rates.get("USD"));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<HistoricalResponse> call, Throwable t) {
        t.printStackTrace();
    }

    public HistoricalResponse getHistorical() {
        return historical;
    }
}