package com.sara.nasaimageexplorer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sara.nasaimageexplorer.database.DatabaseHelper;
import com.sara.nasaimageexplorer.database.FavoriteDao;
import com.sara.nasaimageexplorer.model.FavoriteImage;

/**
 * Displays details of a selected favorite NASA image.
 *
 * @author Sara
 * @version 4.0
 */
public class FavoriteDetailActivity extends AppCompatActivity {

    private ImageView imgFavorite;

    private TextView tvFavoriteTitle;

    private TextView tvFavoriteDate;

    private TextView tvFavoriteUrl;

    private TextView tvFavoriteHdUrl;

    private Button btnDelete;

    private DatabaseHelper databaseHelper;

    private FavoriteDao favoriteDao;

    private FavoriteImage favoriteImage;

    /**
     * Creates the Favorite Detail activity.
     *
     * @param savedInstanceState saved activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_favorite_detail
        );

        imgFavorite =
                findViewById(
                        R.id.imgFavorite
                );

        tvFavoriteTitle =
                findViewById(
                        R.id.tvFavoriteTitle
                );

        tvFavoriteDate =
                findViewById(
                        R.id.tvFavoriteDate
                );

        tvFavoriteUrl =
                findViewById(
                        R.id.tvFavoriteUrl
                );

        tvFavoriteHdUrl =
                findViewById(
                        R.id.tvFavoriteHdUrl
                );

        btnDelete =
                findViewById(
                        R.id.btnDelete
                );

        databaseHelper =
                new DatabaseHelper(this);

        favoriteDao =
                new FavoriteDao(databaseHelper);

        int favoriteId =
                getIntent().getIntExtra(
                        "favorite_id",
                        -1
                );

        if (favoriteId == -1) {

            Toast.makeText(
                    this,
                    "Favorite not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        loadFavorite(favoriteId);

        btnDelete.setOnClickListener(
                v -> deleteFavorite()
        );
    }

    /**
     * Loads the selected favorite from SQLite.
     *
     * @param favoriteId database ID
     */
    private void loadFavorite(int favoriteId) {

        favoriteImage =
                favoriteDao.getFavoriteById(
                        favoriteId
                );

        if (favoriteImage == null) {

            Toast.makeText(
                    this,
                    "Favorite not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        tvFavoriteTitle.setText(
                favoriteImage.getTitle()
        );

        tvFavoriteDate.setText(
                favoriteImage.getDate()
        );

        tvFavoriteUrl.setText(
                favoriteImage.getImageUrl()
        );

        String hdUrl =
                favoriteImage.getHdUrl();

        if (hdUrl != null && !hdUrl.isEmpty()) {

            tvFavoriteHdUrl.setText(
                    hdUrl
            );

        } else {

            tvFavoriteHdUrl.setText(
                    "HD image not available"
            );
        }

        Glide.with(this)
                .load(favoriteImage.getImageUrl())
                .placeholder(
                        android.R.drawable.ic_menu_gallery
                )
                .error(
                        android.R.drawable.ic_menu_report_image
                )
                .into(imgFavorite);
    }

    /**
     * Deletes the current favorite image.
     */
    private void deleteFavorite() {

        if (favoriteImage == null) {

            return;
        }

        boolean deleted =
                favoriteDao.deleteFavorite(
                        favoriteImage.getId()
                );

        if (deleted) {

            Toast.makeText(
                    this,
                    "Removed from Favorites",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Delete failed",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}

