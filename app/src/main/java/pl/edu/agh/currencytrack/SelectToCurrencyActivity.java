package pl.edu.agh.currencytrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.DbHelperExecutor;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.ui.notifications.NotificationSelectAdapter;

public class SelectToCurrencyActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<FavouriteCurrency> currencies = new ArrayList<>();
    private NotificationSelectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_to_currency);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.recyclerView = findViewById(R.id.notificationCurrencySelectRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new NotificationSelectAdapter(this, currencies);
        recyclerView.setAdapter(adapter);
        createList(adapter);

        Button btn = findViewById(R.id.resign);
        btn.setOnClickListener(view -> onBackPressed());
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        String data = adapter.getSelectedShort();
        Intent intent = new Intent();
        intent.putExtra("toCurrencySelected", data);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void createList(NotificationSelectAdapter adapter) {
        List<FavouriteCurrency> elements = DbHelperExecutor.getAllFavouritesAsync(AppDatabase.getDatabase(this));
        this.currencies = elements;
        adapter.setElements(elements);
    }
}

