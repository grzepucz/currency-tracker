package pl.edu.agh.currencytrack;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.Currency;
import pl.edu.agh.currencytrack.ui.favourites.FavouriteItemFragment;
import pl.edu.agh.currencytrack.ui.favourites.dummy.FavouriteContent;

public class MainActivity extends AppCompatActivity implements FavouriteItemFragment.OnListFragmentInteractionListener {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LatestController latestController = new LatestController();
//        latestController.processLatestWithSymbolsRequest();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_favourites_list)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onListFragmentInteraction(FavouriteContent.FavouriteItem item) {

    }

    private void loadDatabase() {
        this.db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "CurrencyTracker").enableMultiInstanceInvalidation().build();

        Currency item = new Currency("EUR", "Euro", "euro.png");
        Currency item2 = new Currency("USD", "Dolar amerykański", "usd.png");
        db.currencyDao().insertAll(item, item2);
    }
}
