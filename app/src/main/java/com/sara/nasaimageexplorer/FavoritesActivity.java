package com.sara.nasaimageexplorer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Displays saved NASA images.
 *
 * @author Sara
 * @version 1.0
 */
public class FavoritesActivity extends AppCompatActivity {


    /**
     * Creates Favorites screen.
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorites);

    }
}