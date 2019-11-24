package pl.edu.agh.currencytrack.ui.favourites;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.currencytrack.R;
import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.DbHelperExecutor;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;

public class FavouritesFragment extends Fragment {

    private FavouritesAdapter favouritesAdapter;
    private RecyclerView favouritesRecyclerView;
    private List<FavouriteCurrency> favourites = new ArrayList<>();
    private AppDatabase mDb;

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.favouritesRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        favouritesAdapter = new FavouritesAdapter(this.getContext(), favourites);
        recyclerView.setAdapter(favouritesAdapter);
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
        createList();
    }

    private void createList() {
        List<FavouriteCurrency> elements = DbHelperExecutor.getAllObservedAsync(AppDatabase.getDatabase(this.getContext()));
        favouritesAdapter.setFavourites(elements);
    }

    @Override
    public void onCreateOptionsMenu(@Nullable Menu menu, @Nullable MenuInflater inflater) {
        inflater.inflate(R.menu.favourites_menu, menu);
    }
}
