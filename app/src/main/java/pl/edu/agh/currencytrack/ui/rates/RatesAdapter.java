package pl.edu.agh.currencytrack.ui.rates;

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
import pl.edu.agh.currencytrack.models.LatestResponse;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.SingleViewHolder> {

    private Context context;
    private List<LatestResponse> rates;

    public RatesAdapter(Context context, List<LatestResponse> rates) {
        this.context = context;
        this.rates = rates;
    }

    public void setLatests(List<LatestResponse> rates) {
        this.rates = new ArrayList<>();
        this.rates = rates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RatesAdapter.SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rate_short, viewGroup, false);
        return new RatesAdapter.SingleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RatesAdapter.SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(rates.get(position));
    }

    @Override
    public int getItemCount() {
        return rates.size();
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
        void bind(final LatestResponse rate) {
            baseNameView.setText(rate != null ? rate.base : "undefined");

            List<String> list = new ArrayList<>();
            rate.rates.forEach((k, v) -> {
                list.add(k + ":      " + v.toString() + System.getProperty("line.separator") + System.getProperty("line.separator"));
            });

            String ratesList = "";

            for (int i = 0; i < list.size(); i++) {
                ratesList = ratesList + list.get(i);
            }

            ratesView.setText(ratesList);

            try {
                baseRateIconImageView.setImageBitmap(ImageHelper.ImageViaAssets(rate.base.toLowerCase(), context));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            itemView.setOnClickListener(view -> {
                if (ratesView.getVisibility() == View.GONE) {
                    ratesView.setVisibility(View.VISIBLE);
                } else {
                    ratesView.setVisibility(View.GONE);
                }
            });
        }
    }
}