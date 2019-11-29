package pl.edu.agh.currencytrack.ui.favourites;

import android.content.Context;
import android.content.Intent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.currencytrack.R;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;
import pl.edu.agh.currencytrack.data.ImageHelper;
import pl.edu.agh.currencytrack.data.NotificationLimit;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.SingleViewHolder> {

    private Context context;
    private List<FavouriteCurrency> favourites;
    private List<NotificationLimit> notifications;
    private Map<String, Boolean> notifyMap = new HashMap<>();

    public FavouritesAdapter(Context context, List<FavouriteCurrency> favourites, List<NotificationLimit> notifications) {
        this.context = context;
        this.favourites = favourites;
        this.notifications = notifications;
    }

    public void setFavourites(List<FavouriteCurrency> favourites) {
        this.favourites = new ArrayList<>();
        this.favourites = favourites;
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setNotifications(List<NotificationLimit> notifications) {
        this.notifications = new ArrayList<>();
        this.notifications = notifications;
        notifications.forEach(i -> this.notifyMap.put(i.getShortName(), i.getShouldNotify()));

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouritesAdapter.SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite, viewGroup, false);
        return new FavouritesAdapter.SingleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull FavouritesAdapter.SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(favourites.get(position));
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        private TextView shortNameView;
        private TextView longNameView;
        private ImageView iconImageView;
        private ImageView shouldNotifyImageView;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            shortNameView = itemView.findViewById(R.id.shortNameView);
            longNameView = itemView.findViewById(R.id.longNameView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            shouldNotifyImageView = itemView.findViewById(R.id.shouldNotifyImageView);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        void bind(final FavouriteCurrency favourite) {
            shortNameView.setText(favourite.getShortName());
            longNameView.setText(favourite.getLongName());
            iconImageView.setImageBitmap(ImageHelper.ImageViaAssets(favourite.getIcon().toLowerCase(), context));
            try {
                shouldNotifyImageView.setVisibility(
                        notifyMap.containsKey(favourite.getShortName()) && (notifyMap.get(favourite.getShortName()) == true)
                                ? View.VISIBLE
                                : View.GONE
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent("android.intent.action.EDIT_NOTIFICATION");
                context.startActivity(intent
                        .putExtra("SHORT", favourite.getShortName())
                );
            });
        }
    }
}