package pl.edu.agh.currencytrack.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import pl.edu.agh.currencytrack.models.TimeseriesResponse;
import pl.edu.agh.currencytrack.services.providers.TimeSeriesDataProviderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Getter
public class TimeseriesController extends Controller implements Callback<TimeseriesResponse> {
    private TimeseriesResponse timeseries;

    public void processTimeseriesRequest() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TimeSeriesDataProviderAPI fixer = retrofit.create(TimeSeriesDataProviderAPI.class);

        //Call<TimeseriesResponse> call = fixer.getLatest(this.secret);
        //call.enqueue(this);
    }

    @Override
    public void onResponse(Call<TimeseriesResponse> call, Response<TimeseriesResponse> response) {
        if(response.isSuccessful()) {
            TimeseriesResponse latest = response.body();
//            System.out.println(latest.rates.size());
//            System.out.println(latest.rates.get("USD"));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<TimeseriesResponse> call, Throwable t) {
        t.printStackTrace();
    }

    public TimeseriesResponse getTimeseries() {
        return timeseries;
    }
}