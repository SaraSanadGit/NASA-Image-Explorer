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
import com.sara.nasaimageexplorer.business.FavoriteService;
import com.sara.nasaimageexplorer.database.DatabaseHelper;
import com.sara.nasaimageexplorer.database.FavoriteDao;
import com.sara.nasaimageexplorer.model.FavoriteImage;

/**

 * Displays details of a selected favorite NASA image.
 *
 * Uses FavoriteService from the Business layer
 * for favorite image operations.
 *
 * @author Sara
 * @version 8.3
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

    private FavoriteService favoriteService;

    private FavoriteImage favoriteImage;

    /**

     * Creates the Favorite Detail activity.
     *
     * @param savedInstanceState saved activity state
     */
    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {

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

        favoriteService =
                new FavoriteService(
                        favoriteDao
                );

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

        tvFavoriteUrl.setOnClickListener(
                v -> openUrl(
                        favoriteImage != null
                                ? favoriteImage.getImageUrl()
                                : ""
                )
        );

        tvFavoriteHdUrl.setOnClickListener(
                v -> {


                    if (favoriteImage != null) {

                        openUrl(
                                favoriteImage.getHdUrl()
                        );
                    }
                }


        );
    }

    /**

     * Loads the selected favorite through
     * the Business layer.
     *
     * @param favoriteId database ID
     */
    private void loadFavorite(
            int favoriteId
    ) {

        favoriteImage =
                favoriteService.getFavoriteById(
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

        if (hdUrl != null
                && !hdUrl.isEmpty()) {


            tvFavoriteHdUrl.setText(
                    hdUrl
            );


        } else {


            tvFavoriteHdUrl.setText(
                    "HD image not available"
            );


        }

        Glide.with(this)
                .load(
                        favoriteImage.getImageUrl()
                )
                .placeholder(
                        android.R.drawable.ic_menu_gallery
                )
                .error(
                        android.R.drawable.ic_menu_report_image
                )
                .into(imgFavorite);
    }

    /**

     * Deletes the current favorite image
     * through the Business layer.
     */
    private void deleteFavorite() {

        if (favoriteImage == null) {


            return;


        }

        boolean deleted =
                favoriteService.deleteFavorite(
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

    /**

     * Opens a URL in the Android browser.
     *
     * @param urlString URL to open
     */
    private void openUrl(
            String urlString
    ) {

        if (urlString == null
                || urlString.isEmpty()
                || urlString.equals(
                "HD image not available"
        )) {


            Toast.makeText(
                    this,
                    "URL not available",
                    Toast.LENGTH_SHORT
            ).show();

            return;


        }

        try {


            Intent browserIntent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(urlString)
                    );

            startActivity(
                    browserIntent
            );


        } catch (Exception e) {


            Toast.makeText(
                    this,
                    "Unable to open URL",
                    Toast.LENGTH_SHORT
            ).show();


        }
    }
}
