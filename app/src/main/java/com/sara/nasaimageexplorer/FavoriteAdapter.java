package com.sara.nasaimageexplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sara.nasaimageexplorer.R;
import com.sara.nasaimageexplorer.model.FavoriteImage;

import java.util.ArrayList;

/**
 * Adapter for displaying favorite NASA images.
 *
 * Displays the image, title, date and delete button.
 *
 * @author Sara
 * @version 3.0
 */
public class FavoriteAdapter extends BaseAdapter {

    private final Context context;

    private final ArrayList<FavoriteImage> favorites;

    private final OnFavoriteClickListener listener;

    /**
     * Listener used for favorite item actions.
     */
    public interface OnFavoriteClickListener {

        /**
         * Called when a favorite image is clicked.
         *
         * @param favorite selected favorite image
         */
        void onFavoriteClick(FavoriteImage favorite);

        /**
         * Called when the delete button is clicked.
         *
         * @param favorite favorite image to delete
         */
        void onDeleteClick(FavoriteImage favorite);
    }

    /**
     * Creates the adapter.
     *
     * @param context activity context
     * @param favorites list of favorite images
     * @param listener listener for item actions
     */
    public FavoriteAdapter(
            Context context,
            ArrayList<FavoriteImage> favorites,
            OnFavoriteClickListener listener
    ) {

        this.context = context;

        this.favorites = favorites;

        this.listener = listener;
    }

    /**
     * Returns number of favorite images.
     *
     * @return number of favorites
     */
    @Override
    public int getCount() {

        return favorites.size();
    }

    /**
     * Returns favorite image at position.
     *
     * @param position item position
     * @return favorite image
     */
    @Override
    public FavoriteImage getItem(int position) {

        return favorites.get(position);
    }

    /**
     * Returns item ID.
     *
     * @param position item position
     * @return database ID
     */
    @Override
    public long getItemId(int position) {

        return favorites.get(position).getId();
    }

    /**
     * Creates each favorite list item.
     *
     * @param position item position
     * @param convertView recycled view
     * @param parent parent view
     * @return favorite item view
     */
    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent
    ) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context)
                    .inflate(
                            R.layout.favorite_item,
                            parent,
                            false
                    );
        }

        ImageView imageView =
                convertView.findViewById(
                        R.id.favoriteImage
                );

        TextView title =
                convertView.findViewById(
                        R.id.favoriteTitle
                );

        TextView date =
                convertView.findViewById(
                        R.id.favoriteDate
                );

        ImageButton deleteButton =
                convertView.findViewById(
                        R.id.btnDeleteFavorite
                );

        FavoriteImage favorite =
                favorites.get(position);

        title.setText(
                favorite.getTitle()
        );

        date.setText(
                favorite.getDate()
        );

        Glide.with(context)
                .load(favorite.getImageUrl())
                .placeholder(
                        android.R.drawable.ic_menu_gallery
                )
                .error(
                        android.R.drawable.ic_menu_report_image
                )
                .into(imageView);

        convertView.setOnClickListener(
                v -> listener.onFavoriteClick(favorite)
        );

        deleteButton.setOnClickListener(
                v -> listener.onDeleteClick(favorite)
        );

        return convertView;
    }
}

