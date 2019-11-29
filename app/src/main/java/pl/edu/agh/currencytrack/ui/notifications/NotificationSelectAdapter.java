package pl.edu.agh.currencytrack.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.agh.currencytrack.R;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.data.ImageHelper;

public class NotificationSelectAdapter extends RecyclerView.Adapter<NotificationSelectAdapter.SingleViewHolder> {

    private Context context;
    private List<FavouriteCurrency> currencies;
    private String selectedName;
    private int checkedPosition = 0;


    public NotificationSelectAdapter(Context context, List<FavouriteCurrency> currencies) {
        this.context = context;
        this.currencies = currencies;
    }

    public String getSelectedShort() {
        return this.selectedName;
    }

    private void setSelectedShort(String name) {
        this.selectedName = name;
    }

    public void setElements(List<FavouriteCurrency> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationSelectAdapter.SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_notification_currency, viewGroup, false);
        return new NotificationSelectAdapter.SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationSelectAdapter.SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(currencies.get(position));
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    private void showToast(String msg) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        private TextView shortNameView;
        private ImageView iconImageView;
        private ImageView isSelectedImageView;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            shortNameView = itemView.findViewById(R.id.shortNameView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            isSelectedImageView = itemView.findViewById(R.id.isSelectedImageView);
        }

        void bind(final FavouriteCurrency favourite) {
            shortNameView.setText(favourite.getShortName());
            iconImageView.setImageBitmap(ImageHelper.ImageViaAssets(favourite.getIcon().toLowerCase(), context));

            if (checkedPosition == -1) {
                isSelectedImageView.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    isSelectedImageView.setVisibility(View.VISIBLE);
                } else {
                    isSelectedImageView.setVisibility(View.GONE);
                }
            }

            itemView.setOnClickListener(view -> {
                isSelectedImageView.setVisibility(View.VISIBLE);
                if (checkedPosition != getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = getAdapterPosition();
                    setSelectedShort(favourite.getShortName());
                    showToast("Selected " + favourite.getLongName());
                }
            });
        }
    }

}