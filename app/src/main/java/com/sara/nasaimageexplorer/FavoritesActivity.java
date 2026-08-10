package com.sara.nasaimageexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.sara.nasaimageexplorer.adapter.FavoriteAdapter;
import com.sara.nasaimageexplorer.database.DatabaseHelper;
import com.sara.nasaimageexplorer.database.FavoriteDao;
import com.sara.nasaimageexplorer.model.FavoriteImage;

import java.util.ArrayList;

/**
 * Displays saved NASA favorite images.
 *
 * Uses FavoriteDao for database access.
 *
 * @author Sara
 * @version 3.0
 */
public class FavoritesActivity extends AppCompatActivity {

    private ListView listFavorites;

    private DatabaseHelper databaseHelper;

    private FavoriteDao favoriteDao;

    private ArrayList<FavoriteImage> favorites;

    private FavoriteAdapter adapter;

    /**
     * Creates the Favorites activity.
     *
     * @param savedInstanceState saved activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorites);

        listFavorites =
                findViewById(
                        R.id.listFavorites
                );

        databaseHelper =
                new DatabaseHelper(this);

        favoriteDao =
                new FavoriteDao(databaseHelper);

        loadFavorites();
    }

    /**
     * Loads favorite images from the database.
     */
    private void loadFavorites() {

        favorites =
                favoriteDao.getFavorites();

        adapter =
                new FavoriteAdapter(
                        this,
                        favorites,
                        new FavoriteAdapter.OnFavoriteClickListener() {

                            @Override
                            public void onFavoriteClick(
                                    FavoriteImage favorite
                            ) {

                                openFavoriteDetails(favorite);
                            }

                            @Override
                            public void onDeleteClick(
                                    FavoriteImage favorite
                            ) {

                                deleteFavorite(favorite);
                            }
                        }
                );

        listFavorites.setAdapter(adapter);
    }

    /**
     * Opens the details screen for a favorite image.
     *
     * @param favorite selected favorite image
     */
    private void openFavoriteDetails(
            FavoriteImage favorite
    ) {

        Intent intent =
                new Intent(
                        FavoritesActivity.this,
                        FavoriteDetailActivity.class
                );

        intent.putExtra(
                "favorite_id",
                favorite.getId()
        );

        startActivity(intent);
    }

    /**
     * Deletes a favorite image.
     *
     * @param favorite favorite image to delete
     */
    private void deleteFavorite(
            FavoriteImage favorite
    ) {

        boolean deleted =
                favoriteDao.deleteFavorite(
                        favorite.getId()
                );

        if (deleted) {

            favorites.remove(favorite);

            adapter.notifyDataSetChanged();

            Toast.makeText(
                    this,
                    "Removed from Favorites",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "Delete failed",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    /**
     * Reloads favorites when returning to this activity.
     */
    @Override
    protected void onResume() {

        super.onResume();

        if (favoriteDao != null) {

            loadFavorites();
        }
    }
}

