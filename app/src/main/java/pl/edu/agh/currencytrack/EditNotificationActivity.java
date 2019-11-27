package pl.edu.agh.currencytrack;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.DbHelperExecutor;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.data.ImageHelper;
import pl.edu.agh.currencytrack.data.NotificationLimit;
import pl.edu.agh.currencytrack.ui.notifications.NotificationSelectAdapter;

public class EditNotificationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private AppDatabase mDb;
    private RecyclerView selectRecyclerView;
    private NotificationSelectAdapter adapter;
    private FavouriteCurrency base;
    private NotificationLimit notification;
    private Boolean shouldNotify = true;

    private Button btnUpdateSelected;
    private Button btnSelectCurrency;
    private String currency;
    private String toCurrency;
    private ImageView currencyIcon;
    private ImageView toCurrencyIcon;
    private EditText limitEditText;
    private TextView currencyShortName;
    private Switch switchShouldNotify;
    private RecyclerView recyclerView;
    private List<String> currencies = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setTitle("Edit notification");

        this.currency = getIntent().getStringExtra("SHORT");
        this.currencies.add(this.currency);
        this.base = DbHelperExecutor.getFavouriteByShortAsync(AppDatabase.getDatabase(this), this.currency);
        List<NotificationLimit> dbResult = DbHelperExecutor.getNotificationAsync(AppDatabase.getDatabase(this), this.currencies);
        this.notification = dbResult.isEmpty() ? null : dbResult.get(0);
        createForm();
    }

    private void createForm() {
        this.btnUpdateSelected = (Button) findViewById(R.id.btnUpdateSelected);
        this.btnSelectCurrency = (Button) findViewById(R.id.btnSelectToCurrency);
        this.currencyIcon = (ImageView) findViewById(R.id.currencyIcon);
        this.toCurrencyIcon = (ImageView) findViewById(R.id.toCurrencyIcon);
        this.limitEditText = (EditText) findViewById(R.id.limitEditText);
        this.currencyShortName = (TextView) findViewById(R.id.currencyShortName);
        this.switchShouldNotify = (Switch) findViewById(R.id.switchShouldNotify);

        String name = this.base.getShortName() + " | " + this.base.getLongName();
        currencyShortName.setText(name);
        currencyIcon.setImageBitmap(ImageHelper.ImageViaAssets(this.currency.toLowerCase(), getApplicationContext()));

        btnUpdateSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification();
                showToast("Updated");
                finish();
            }
        });

        btnSelectCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SELECT_TO_CURRENCY");
                startActivityForResult(intent, 1);
            }
        });

        this.switchShouldNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shouldNotify = isChecked;
            }
        });

        updateForm();
    }

    private void updateForm() {
        if (this.notification != null) {
            this.btnSelectCurrency.setText(this.notification.getToCurrency());
            this.limitEditText.setText(String.valueOf(this.notification.getLimit()));
            this.switchShouldNotify.setChecked(this.notification.getShouldNotify());

            if (this.notification.getToCurrency() != null) {
                this.toCurrencyIcon.setImageBitmap(ImageHelper.ImageViaAssets(this.notification.getToCurrency().toLowerCase(), getApplicationContext()));
            }
        }
    }

    private void updateNotification() {
        Double limit = Double.valueOf(this.limitEditText.getText().toString());
        List<NotificationLimit> elem = new ArrayList<>();

        if (this.notification != null) {
            this.notification.setShouldNotify(this.shouldNotify);
            this.notification.setLimit(limit);
            this.notification.setToCurrency(this.toCurrency);
            elem.add(this.notification);

            DbHelperExecutor.updateNotificationElementAsync(AppDatabase.getDatabase(this), elem);
        } else {
            NotificationLimit notification = new NotificationLimit(this.currency, this.toCurrency, limit, this.shouldNotify);
            elem.add(notification);
            DbHelperExecutor.insertNotificationElementAsync(AppDatabase.getDatabase(this), elem);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if ((resultCode == RESULT_OK) && data.getStringExtra("toCurrencySelected") != null) {
                String selected = data.getStringExtra("toCurrencySelected");
                this.toCurrency = selected;
                this.btnSelectCurrency.setText(selected);
                this.toCurrencyIcon.setImageBitmap(ImageHelper.ImageViaAssets(selected.toLowerCase(), getApplicationContext()));
            }
        } else {
            showToast("You can try one more time");
        }
    }
}

