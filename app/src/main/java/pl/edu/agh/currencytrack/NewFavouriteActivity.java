package pl.edu.agh.currencytrack;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.DbHelperExecutor;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.ui.favourites.FavouritesNewAdapter;

public class NewFavouriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FavouriteCurrency> favourites = new ArrayList<>();
    private FavouritesNewAdapter adapter;
    private AppCompatButton btnAddSelected;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_new);

        this.btnAddSelected = (AppCompatButton) findViewById(R.id.btnAddSelected);
        this.recyclerView = (RecyclerView) findViewById(R.id.favouritesEditRecyclerView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setTitle("Select currencies to track");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new FavouritesNewAdapter(this, favourites);
        recyclerView.setAdapter(adapter);

        createList();

        btnAddSelected.setOnClickListener(view -> {
            if (adapter.getSelected() != null) {
                showToast(adapter.getSelected().size() + " elements");
                updateItems(adapter.getSelected());
                adapter.resetSelected();
            } else {
                showToast("No Selection");
            }

            finish();
        });
    }

    private void updateItems(List<String> indexes) {
        DbHelperExecutor.updateElementListObservationAsync(AppDatabase.getDatabase(this), indexes);
        createList();
    }

    private void createList() {
        List<FavouriteCurrency> elements = DbHelperExecutor.getAllFavouritesAsync(AppDatabase.getDatabase(this));
        adapter.setFavourites(elements);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
