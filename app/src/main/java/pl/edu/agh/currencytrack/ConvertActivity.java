package pl.edu.agh.currencytrack;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pl.edu.agh.currencytrack.data.ImageHelper;
import pl.edu.agh.currencytrack.data.ListHelper;
import pl.edu.agh.currencytrack.models.ConvertResponse;
import pl.edu.agh.currencytrack.services.providers.ConvertDataProviderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConvertActivity extends AppCompatActivity {

    Button btnFromCurrency;
    Button btnToCurrency;
    Button btnConvert;
    EditText amountEditText;
    TextView resultTextView;
    Integer clicked = -1;
    Double amount = 1.0;

    String secret = "048fdb45f003ea89518104c677d4cf0f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Converter");

        this.btnFromCurrency = (Button) findViewById(R.id.btnFrom);
        this.btnToCurrency = (Button) findViewById(R.id.btnTo);
        this.btnConvert = (Button) findViewById(R.id.btnConvert);
        this.amountEditText = (EditText) findViewById(R.id.amountEditText);
        this.resultTextView = (TextView) findViewById(R.id.convertResult);

        btnFromCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SELECT_TO_CURRENCY");
                startActivityForResult(intent, 1);
                clicked = 0;
            }
        });

        btnToCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SELECT_TO_CURRENCY");
                startActivityForResult(intent, 1);
                clicked = 1;
            }
        });

        amountEditText.setText(String.valueOf(amount));

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://data.fixer.io/api/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                ConvertDataProviderAPI fixer = retrofit.create(ConvertDataProviderAPI.class);

                Call<ConvertResponse> call = fixer.convertFromTo(
                        secret,
                        btnFromCurrency.getText().toString(),
                        btnToCurrency.getText().toString(),
                        Double.valueOf(amountEditText.getText().toString())
                );

                call.enqueue(new Callback<ConvertResponse>() {
                    @Override
                    public void onResponse(Call<ConvertResponse> call, Response<ConvertResponse> response) {
                        if(response.isSuccessful() && response.body().success) {
                            resultTextView.setText(String.valueOf(response.body().result));
                        } else {
                            System.out.println(response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ConvertResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if ((resultCode == RESULT_OK) && data.getStringExtra("toCurrencySelected") != null) {
                String selected = data.getStringExtra("toCurrencySelected");
                setButtonText(selected);
            }
        } else {
            showToast("You can try one more time");
        }
    }

    private void setButtonText(String selected) {
        if (clicked == 0) {
            btnFromCurrency.setText(selected);
        } else {
            btnToCurrency.setText(selected);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
