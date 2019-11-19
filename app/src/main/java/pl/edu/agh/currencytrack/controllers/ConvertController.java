package pl.edu.agh.currencytrack.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import pl.edu.agh.currencytrack.models.ConvertResponse;
import pl.edu.agh.currencytrack.services.providers.ConvertDataProviderApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Getter
public class ConvertController extends Controller implements Callback<ConvertResponse> {
    private ConvertResponse convert;

    public void processConvertRequest(String from, String to, Double amount) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ConvertDataProviderApi fixer = retrofit.create(ConvertDataProviderApi.class);

        Call<ConvertResponse> call = fixer.convertFromTo(this.secret, from, to, amount);
        call.enqueue(this);
    }

    public void processConvertWithDateRequest(String from, String to, Double amount, String date) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ConvertDataProviderApi fixer = retrofit.create(ConvertDataProviderApi.class);

        Call<ConvertResponse> call = fixer.convertFromToWithDate(this.secret, from, to, amount, date);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ConvertResponse> call, Response<ConvertResponse> response) {
        if(response.isSuccessful()) {
            ConvertResponse respone = response.body();
            System.out.println(call.request().url().toString());
            System.out.println(respone.success);
            System.out.println(respone.query.to);
            System.out.println(respone.result);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<ConvertResponse> call, Throwable t) {
        t.printStackTrace();
    }

    public ConvertResponse getConvert() {
        return convert;
    }
}