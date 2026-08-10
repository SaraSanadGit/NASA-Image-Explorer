package com.sara.nasaimageexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sara.nasaimageexplorer.adapter.FavoriteAdapter;
import com.sara.nasaimageexplorer.business.FavoriteService;
import com.sara.nasaimageexplorer.database.DatabaseHelper;
import com.sara.nasaimageexplorer.database.FavoriteDao;
import com.sara.nasaimageexplorer.model.FavoriteImage;

import java.util.ArrayList;

/**

 * Displays saved NASA favorite images.
 *
 * Uses FavoriteService from the Business layer
 * for favorite image operations.
 *
 * @author Sara
 * @version 8.2
 */
public class FavoritesActivity extends AppCompatActivity {

    private ListView listFavorites;

    private DatabaseHelper databaseHelper;

    private FavoriteDao favoriteDao;

    private FavoriteService favoriteService;

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

        setContentView(
                R.layout.activity_favorites
        );

        listFavorites =
                findViewById(
                        R.id.listFavorites
                );

        databaseHelper =
                new DatabaseHelper(this);

        favoriteDao =
                new FavoriteDao(databaseHelper);

        favoriteService =
                new FavoriteService(
                        favoriteDao
                );

        loadFavorites();
    }

    /**

     * Loads favorite images through the Business layer.
     */
    private void loadFavorites() {

        favorites =
                favoriteService.getFavorites();

        adapter =
                new FavoriteAdapter(
                        this,
                        favorites,
                        new FavoriteAdapter.OnFavoriteClickListener() {


                            @Override
                            public void onFavoriteClick(
                                    FavoriteImage favorite
                            ) {

                                openFavoriteDetails(
                                        favorite
                                );
                            }

                            @Override
                            public void onDeleteClick(
                                    FavoriteImage favorite
                            ) {

                                deleteFavorite(
                                        favorite
                                );
                            }
                        }
                );


        listFavorites.setAdapter(
                adapter
        );
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

     * Deletes a favorite image through the Business layer.
     *
     * @param favorite favorite image to delete
     */
    private void deleteFavorite(
            FavoriteImage favorite
    ) {

        boolean deleted =
                favoriteService.deleteFavorite(
                        favorite.getId()
                );

        if (deleted) {


            favorites.remove(
                    favorite
            );

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

        if (favoriteService != null) {


            loadFavorites();


        }
    }
}
