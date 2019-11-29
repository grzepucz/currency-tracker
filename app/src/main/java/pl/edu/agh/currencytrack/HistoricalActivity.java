package pl.edu.agh.currencytrack;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.DbHelperExecutor;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.data.ListHelper;
import pl.edu.agh.currencytrack.models.HistoricalResponse;
import pl.edu.agh.currencytrack.services.providers.HistoricalDataProviderAPI;
import pl.edu.agh.currencytrack.ui.historical.HistoricalAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoricalActivity extends AppCompatActivity {

    private String secret = BuildConfig.API_SECRET;
    private List<HistoricalResponse> responseList = new ArrayList<>();
    private List<String> ratesShorts = new ArrayList<>();
    private HistoricalAdapter historicalAdapter;
    private String date;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.date = getIntent().getStringExtra("dateString");

        RecyclerView recyclerView = findViewById(R.id.historicalRatesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        historicalAdapter = new HistoricalAdapter(this, responseList);
        recyclerView.setAdapter(historicalAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TextView date = findViewById(R.id.dateTextView);
        date.setText(this.date);
        createList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createList() {
        try {
            List<FavouriteCurrency> elements = DbHelperExecutor.getAllObservedAsync(AppDatabase.getDatabase(getApplicationContext()))
                    .stream()
                    .peek(i -> ratesShorts.add(i.getShortName()))
                    .collect(Collectors.toList());

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://data.fixer.io/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            HistoricalDataProviderAPI fixer = retrofit.create(HistoricalDataProviderAPI.class);

            elements.forEach(i -> {
                Call<HistoricalResponse> call = fixer.getHistoricalWithBaseAndSymbols(date, secret, i.getShortName(), ListHelper.flatListToStringWithDelimiter(ratesShorts, ","));
                call.enqueue(new Callback<HistoricalResponse>() {
                    @Override
                    public void onResponse(Call<HistoricalResponse> call, Response<HistoricalResponse> response) {
                        if (response.isSuccessful() && response.body().success) {
                            responseList.add(response.body());
                            historicalAdapter.setHistorical(responseList);
                        } else {
                            System.out.println(response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoricalResponse> call, Throwable t) {

                    }
                });
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
