package pl.edu.agh.currencytrack.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import pl.edu.agh.currencytrack.models.FluctuationResponse;
import pl.edu.agh.currencytrack.services.providers.FluctuationDataProviderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Getter
public class FluctuationController extends Controller implements Callback<FluctuationResponse> {
    private FluctuationResponse fluctuation;

    public void processFluctuationResponse() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FluctuationDataProviderAPI fixer = retrofit.create(FluctuationDataProviderAPI.class);

//        Call<FluctuationResponse> call = fixer.getLatest(this.secret);
//        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<FluctuationResponse> call, Response<FluctuationResponse> response) {
        if(response.isSuccessful()) {
            FluctuationResponse latest = response.body();
//            System.out.println(latest.rates.size());
//            System.out.println(latest.rates.get("USD"));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<FluctuationResponse> call, Throwable t) {
        t.printStackTrace();
    }

    public FluctuationResponse getFluctuation() {
        return fluctuation;
    }
}