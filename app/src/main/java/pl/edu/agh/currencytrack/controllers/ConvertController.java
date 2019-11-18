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

    public void processConvertRequest() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ConvertDataProviderApi fixer = retrofit.create(ConvertDataProviderApi.class);

       // Call<ConvertResponse> call = fixer.getLatest(this.secret);
       // call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ConvertResponse> call, Response<ConvertResponse> response) {
        if(response.isSuccessful()) {
            ConvertResponse convert = response.body();
//            System.out.println(latest.rates.size());
//            System.out.println(latest.rates.get("USD"));
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