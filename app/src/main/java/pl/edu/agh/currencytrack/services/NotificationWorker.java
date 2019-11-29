package pl.edu.agh.currencytrack.services;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.stream.Collectors;

import pl.edu.agh.currencytrack.BuildConfig;
import pl.edu.agh.currencytrack.R;
import pl.edu.agh.currencytrack.data.AppDatabase;
import pl.edu.agh.currencytrack.data.DbHelperExecutor;
import pl.edu.agh.currencytrack.data.NotificationLimit;
import pl.edu.agh.currencytrack.models.LatestResponse;
import pl.edu.agh.currencytrack.services.providers.LatestDataProviderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationWorker extends Worker {

    private String CHANNEL_ID = BuildConfig.CHANNEL_ID;
    private String secret = BuildConfig.API_SECRET;

    public NotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Result doWork() {
        List<NotificationLimit> notificationsLimits = DbHelperExecutor.getAllNotificationsAsync(AppDatabase.getDatabase(getApplicationContext()))
                .stream()
                .filter(i -> i.getShouldNotify())
                .collect(Collectors.toList());

        notificationsLimits.forEach(this::makeRequest);

        return Result.success();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void makeRequest(NotificationLimit notification) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LatestDataProviderAPI fixer = retrofit.create(LatestDataProviderAPI.class);

        Call<LatestResponse> call = fixer.getLatestWithBaseAndSymbols(secret, notification.getShortName(), notification.getToCurrency());
        call.enqueue(new Callback<LatestResponse>() {

            @Override
            public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {
                if(response.isSuccessful() && response.body().success) {
                    if (shouldMakeNotification(notification.getLimit(), notification.getToCurrency(), response.body())) {
                        String from = notification.getShortName();
                        String to = notification.getToCurrency();
                        String limit = String.valueOf(notification.getLimit());

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.drawable.notify)
                                .setContentTitle("Rate is above limit!")
                                .setContentText(from + " to " + to + " had reached your limit rate: " + limit)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                        notificationManager.notify((int) (Math.random()*1639/4), builder.build());
                    }
                }
            }

            @Override
            public void onFailure(Call<LatestResponse> call, Throwable t) {

            }

            private Boolean shouldMakeNotification(Double limit, String key, LatestResponse response) {
                try {
                    return response.rates.get(key) >= limit;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;
            }
        });
    }
}
