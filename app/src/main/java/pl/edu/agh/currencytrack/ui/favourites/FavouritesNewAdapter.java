package pl.edu.agh.currencytrack.ui.favourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.agh.currencytrack.R;
import pl.edu.agh.currencytrack.data.FavouriteCurrency;

import java.util.ArrayList;
import java.util.List;

public class FavouritesNewAdapter extends RecyclerView.Adapter<FavouritesNewAdapter.SingleViewHolder> {

    private Context context;
    private List<FavouriteCurrency> favourites;    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private ArrayList<String> checkedPositions;

    public FavouritesNewAdapter(Context context, List<FavouriteCurrency> favourites) {
        this.context = context;
        this.favourites = favourites;
        this.checkedPositions = new ArrayList<>();
    }

    public void setFavourites(List<FavouriteCurrency>  favourites) {
        this.favourites = new ArrayList<>();
        this.favourites = favourites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_favourite, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(favourites.get(position));
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        private TextView shortNameView;
        private TextView longNameView;
        private ImageView isSelectedImageView;
        private ImageView iconImageView;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            shortNameView = itemView.findViewById(R.id.shortNameView);
            longNameView = itemView.findViewById(R.id.longNameView);
            isSelectedImageView = itemView.findViewById(R.id.isSelectedImageView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
        }

        void bind(final FavouriteCurrency favourite) {
            isSelectedImageView.setVisibility(favourite.getObserved() ? View.VISIBLE : View.GONE);
            shortNameView.setText(favourite.getShortName());
            longNameView.setText(favourite.getLongName());
            iconImageView.setImageResource(R.drawable.common_google_signin_btn_text_light_normal); // xdd

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favourite.setObserved(!favourite.getObserved());
                    isSelectedImageView.setVisibility(favourite.getObserved() ? View.VISIBLE : View.GONE);
                    updateChecked(favourite.getShortName());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    private void updateChecked(String name) {
        if (checkedPositions.contains(name)) {
            checkedPositions.remove(name);
        } else {
            checkedPositions.add(name);
        }
    }

    public ArrayList<String> getSelected() {
        if (!checkedPositions.isEmpty()) {
            return checkedPositions;
        }
        return null;
    }

    public void resetSelected() {
        checkedPositions.clear();
    }
}