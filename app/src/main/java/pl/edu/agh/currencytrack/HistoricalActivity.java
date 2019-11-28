package pl.edu.agh.currencytrack;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.DbHelperExecutor;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.data.ListHelper;
import pl.edu.agh.currencytrack.models.HistoricalResponse;
import pl.edu.agh.currencytrack.models.LatestResponse;
import pl.edu.agh.currencytrack.services.providers.HistoricalDataProviderAPI;
import pl.edu.agh.currencytrack.services.providers.LatestDataProviderAPI;
import pl.edu.agh.currencytrack.ui.rates.RatesAdapter;
import pl.edu.agh.currencytrack.ui.tools.HistoricalAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoricalActivity extends AppCompatActivity {

    String secret = "048fdb45f003ea89518104c677d4cf0f";
    String date;
    private List<HistoricalResponse> responseList = new ArrayList<>();
    private List<String> ratesShorts = new ArrayList<>();
    private HistoricalAdapter historicalAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.date = getIntent().getStringExtra("dateString");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.historicalRatesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        historicalAdapter = new HistoricalAdapter(this, responseList);
        recyclerView.setAdapter(historicalAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TextView date = (TextView) findViewById(R.id.dateTextView);
        date.setText(this.date);
        createList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createList() {
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
            Call<HistoricalResponse> call = fixer.getHistoricalWithBaseAndSymbols(date ,secret, i.getShortName(), ListHelper.flatListToStringWithDelimiter(ratesShorts, ","));
            call.enqueue(new Callback<HistoricalResponse>() {
                @Override
                public void onResponse(Call<HistoricalResponse> call, Response<HistoricalResponse> response) {
                    if(response.isSuccessful() && response.body().success) {
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
    }
}
