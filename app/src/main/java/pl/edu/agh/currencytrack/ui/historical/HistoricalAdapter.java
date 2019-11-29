package pl.edu.agh.currencytrack.ui.historical;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.currencytrack.R;
import pl.edu.agh.currencytrack.data.ImageHelper;
import pl.edu.agh.currencytrack.models.HistoricalResponse;

public class HistoricalAdapter extends RecyclerView.Adapter<HistoricalAdapter.SingleViewHolder>  {

    private Context context;
    private List<HistoricalResponse> historical;
    private List<String> shorts;

    public HistoricalAdapter(Context context, List<HistoricalResponse> historical) {
        this.context = context;
        this.historical = historical;
    }

    public void setHistorical(List<HistoricalResponse>  responses) {
        this.historical = new ArrayList<>();
        this.historical = responses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoricalAdapter.SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_historical, viewGroup, false);
        return new HistoricalAdapter.SingleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull HistoricalAdapter.SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(historical.get(position));
    }

    @Override
    public int getItemCount() {
        return historical.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        private TextView baseNameView;
        private TextView ratesView;
        private ImageView baseRateIconImageView;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            baseNameView = itemView.findViewById(R.id.baseNameView);
            ratesView = itemView.findViewById(R.id.ratesView);
            baseRateIconImageView = itemView.findViewById(R.id.baseRateIconImageView);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        void bind(final HistoricalResponse response) {
            baseNameView.setText(response != null ? response.base : "undefined");
            baseRateIconImageView.setImageBitmap(ImageHelper.ImageViaAssets(response.base.toLowerCase() + ".png", context));

            List<String> list = new ArrayList<>();
            response.rates.forEach((k, v) -> {
                list.add(k + ":      " + v.toString() + System.getProperty ("line.separator") + System.getProperty ("line.separator"));
            });

            String ratesList = "";

            for (int i=0; i<list.size(); i++) {
                ratesList = ratesList + list.get(i);
            }

            ratesView.setText(ratesList);
            ratesView.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ratesView.getVisibility() == View.GONE) {
                        ratesView.setVisibility(View.VISIBLE);
                    } else {
                        ratesView.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}