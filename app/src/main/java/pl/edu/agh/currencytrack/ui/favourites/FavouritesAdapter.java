package pl.edu.agh.currencytrack.ui.favourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.currencytrack.R;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.SingleViewHolder>  {

    private Context context;
    private List<FavouriteCurrency> favourites;

    public FavouritesAdapter(Context context, List<FavouriteCurrency> favourites) {
        this.context = context;
        this.favourites = favourites;
    }

    public void setFavourites(List<FavouriteCurrency>  favourites) {
        this.favourites = new ArrayList<>();
        this.favourites = favourites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouritesAdapter.SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite, viewGroup, false);
        return new FavouritesAdapter.SingleViewHolder(view);
    }

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

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            shortNameView = itemView.findViewById(R.id.shortNameView);
            longNameView = itemView.findViewById(R.id.longNameView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
        }

        void bind(final FavouriteCurrency favourite) {
            shortNameView.setText(favourite.getShortName());
            longNameView.setText(favourite.getLongName());
            iconImageView.setImageResource(R.drawable.common_google_signin_btn_text_light_normal); // xdd

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Przeniesie
                }
            });
        }
    }
}