package pl.edu.agh.currencytrack;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.DbHelperExecutor;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.data.ImageHelper;
import pl.edu.agh.currencytrack.data.NotificationLimit;

public class EditNotificationActivity extends AppCompatActivity {

    private FavouriteCurrency base;
    private NotificationLimit notification;
    private Boolean shouldNotify = true;

    private Button btnSelectCurrency;
    private String currency;
    private String toCurrency;
    private ImageView toCurrencyIcon;
    private EditText limitEditText;
    private Switch switchShouldNotify;
    private List<String> currencies = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_edit);

        this.currency = getIntent().getStringExtra("SHORT");
        this.currencies.add(this.currency);
        this.base = DbHelperExecutor.getFavouriteByShortAsync(AppDatabase.getDatabase(this), this.currency);
        List<NotificationLimit> dbResult = DbHelperExecutor.getNotificationAsync(AppDatabase.getDatabase(this), this.currencies);

        try {
            getSupportActionBar().setTitle("Edit notification");
            this.notification = dbResult.isEmpty() ? null : dbResult.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        createForm();
    }

    private void createForm() {
        Button btnUpdateSelected = findViewById(R.id.btnUpdateSelected);
        this.btnSelectCurrency = findViewById(R.id.btnSelectToCurrency);
        ImageView currencyIcon = findViewById(R.id.currencyIcon);
        this.toCurrencyIcon = findViewById(R.id.toCurrencyIcon);
        this.limitEditText = findViewById(R.id.limitEditText);
        TextView currencyShortName = findViewById(R.id.currencyShortName);
        this.switchShouldNotify = findViewById(R.id.switchShouldNotify);

        String name = this.base.getShortName() + " | " + this.base.getLongName();
        currencyShortName.setText(name);
        currencyIcon.setImageBitmap(ImageHelper.ImageViaAssets(this.currency.toLowerCase(), getApplicationContext()));

        this.switchShouldNotify.setOnCheckedChangeListener((buttonView, isChecked) -> shouldNotify = isChecked);

        btnUpdateSelected.setOnClickListener(view -> {
            updateNotification();
            showToast("Updated");
            finish();
        });

        btnSelectCurrency.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.SELECT_TO_CURRENCY");
            startActivityForResult(intent, 1);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if ((resultCode == RESULT_OK) && data.getStringExtra("toCurrencySelected") != null) {
                String selected = data.getStringExtra("toCurrencySelected");
                this.toCurrency = selected;
                this.btnSelectCurrency.setText(selected);

                try {
                    this.toCurrencyIcon.setImageBitmap(ImageHelper.ImageViaAssets(selected.toLowerCase(), getApplicationContext()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            showToast("You can try one more time");
        }
    }
}

