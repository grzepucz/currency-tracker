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

    public void processTimeseriesFromToRequest(String from, String to) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TimeSeriesDataProviderAPI fixer = retrofit.create(TimeSeriesDataProviderAPI.class);

        Call<TimeseriesResponse> call = fixer.getTimeseriesFromTo(this.secret, from, to);
        call.enqueue(this);
    }

    public void processTimeseriesFromToWithBaseRequest(String from, String to, String base) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TimeSeriesDataProviderAPI fixer = retrofit.create(TimeSeriesDataProviderAPI.class);

        Call<TimeseriesResponse> call = fixer.getTimeseriesFromToWithBase(this.secret, from, to, base);
        call.enqueue(this);
    }

    public void processTimeseriesFromToWithSymbolsRequest(String from, String to, String symbols) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TimeSeriesDataProviderAPI fixer = retrofit.create(TimeSeriesDataProviderAPI.class);

        Call<TimeseriesResponse> call = fixer.getTimeseriesFromToWithSymbols(this.secret, from, to, symbols);
        call.enqueue(this);
    }

    public void processTimeseriesFromToWithBaseAndSymbolsRequest(String from, String to, String base, String symbols) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TimeSeriesDataProviderAPI fixer = retrofit.create(TimeSeriesDataProviderAPI.class);

        Call<TimeseriesResponse> call = fixer.getTimeseriesFromToWithBaseAndSymbols(this.secret, from, to, base, symbols);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<TimeseriesResponse> call, Response<TimeseriesResponse> response) {
        if(response.isSuccessful()) {
            TimeseriesResponse timeseries = response.body();
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