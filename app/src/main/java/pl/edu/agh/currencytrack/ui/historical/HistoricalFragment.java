package pl.edu.agh.currencytrack.ui.historical;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Date;

import pl.edu.agh.currencytrack.R;

public class HistoricalFragment extends Fragment {
    public static HistoricalFragment newInstance() {
        return new HistoricalFragment();
    }

    private String pickedDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.setHasOptionsMenu(false);

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.pickedDate = formatter.format(now);

        View rootView = inflater.inflate(R.layout.fragment_historical, container, false);

        CalendarView calendar = rootView.findViewById(R.id.calendarView);
        Button historical = rootView.findViewById(R.id.btnGetHistorical);

        calendar.setDate(now.getTime());
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String monthStr = month < 10 ? "0" + month : String.valueOf(month);
                String dayStr = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                pickedDate = year + "-" + monthStr + "-" + dayStr;
            }
        });

        historical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.HISTORICAL");
                intent.putExtra("dateString", pickedDate);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(@Nullable Menu menu, @Nullable MenuInflater inflater) {
        inflater.inflate(R.menu.favourites_menu, menu);
    }
}
