package pl.edu.agh.currencytrack.ui.rates;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.agh.currencytrack.BuildConfig;
import pl.edu.agh.currencytrack.R;
import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.DbHelperExecutor;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.data.ListHelper;
import pl.edu.agh.currencytrack.models.LatestResponse;
import pl.edu.agh.currencytrack.services.providers.LatestDataProviderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RatesFragment extends Fragment {

    private RatesAdapter ratesAdapter;
    private List<LatestResponse> rates = new ArrayList<>();
    private List<String> ratesShorts = new ArrayList<>();
    private String secret = BuildConfig.API_SECRET;

    public static RatesFragment newInstance() {
        return new RatesFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rates, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.ratesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ratesAdapter = new RatesAdapter(this.getContext(), rates, ratesShorts);
        recyclerView.setAdapter(ratesAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        createList();

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
       // createList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createList() {
        List<FavouriteCurrency> elements = DbHelperExecutor.getAllObservedAsync(AppDatabase.getDatabase(this.getContext()));

        if (elements != null && rates.isEmpty()) {
            elements.stream().map(i -> ratesShorts.add(i.getShortName())).collect(Collectors.toList());

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://data.fixer.io/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            LatestDataProviderAPI fixer = retrofit.create(LatestDataProviderAPI.class);

            for (int i = 0; i < ratesShorts.size(); i++) {

                Call<LatestResponse> call = fixer.getLatestWithBaseAndSymbols(secret, ratesShorts.get(i), ListHelper.flatListToStringWithDelimiter(ratesShorts, ","));
                call.enqueue(new Callback<LatestResponse>() {

                    @Override
                    public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {
                        if(response.isSuccessful() && response.body().success) {
                            rates.add(response.body());
                            ratesAdapter.setLatests(rates);
                        } else {
                            System.out.println(response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<LatestResponse> call, Throwable t) {

                    }
                });
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@Nullable Menu menu, @Nullable MenuInflater inflater) {
        inflater.inflate(R.menu.favourites_menu, menu);
    }
}