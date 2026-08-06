package com.sara.nasaimageexplorer;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.sara.nasaimageexplorer.fragment.HomeFragment;

/**
 * Main Activity of NASA Image Explorer application.
 * This activity contains the Toolbar, Navigation Drawer,
 * and the main Fragment container.
 *
 * @author Sara
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    /**
     * Called when activity is created.
     *
     * @param savedInstanceState saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this,
                        drawerLayout,
                        toolbar,
                        R.string.open_drawer,
                        R.string.close_drawer
                );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.fragment_container,
                            new HomeFragment()
                    )
                    .commit();

        }


        navigationView.setNavigationItemSelectedListener(
                item -> {

                    int id = item.getItemId();

                    if (id == R.id.nav_search) {

                        // Search activity will be added later

                    } else if (id == R.id.nav_favorites) {

                        // Favorites activity will be added later
                    }

                    drawerLayout.closeDrawers();

                    return true;
                }
        );
    }


    /**
     * Handles toolbar menu selections.
     *
     * @param item selected menu item
     * @return true if handled
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}