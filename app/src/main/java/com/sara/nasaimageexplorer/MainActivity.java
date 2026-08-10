package com.sara.nasaimageexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.sara.nasaimageexplorer.fragment.HomeFragment;

/**
 * Main Activity.
 *
 * Contains Toolbar, Navigation Drawer and Home Fragment.
 *
 * @author Sara
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private Toolbar toolbar;

    /**
     * Creates the Main Activity.
     *
     * @param savedInstanceState saved activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar =
                findViewById(R.id.toolbar);

        drawerLayout =
                findViewById(R.id.drawer_layout);

        navigationView =
                findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle(
                    "NASA Image Explorer - Version 1.0"
            );
        }

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

                    int id =
                            item.getItemId();

                    if (id == R.id.nav_home) {

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(
                                        R.id.fragment_container,
                                        new HomeFragment()
                                )
                                .commit();

                    } else if (id == R.id.nav_search) {

                        startActivity(
                                new Intent(
                                        this,
                                        SearchActivity.class
                                )
                        );

                    } else if (id == R.id.nav_favorites) {

                        startActivity(
                                new Intent(
                                        this,
                                        FavoritesActivity.class
                                )
                        );

                    } else if (id == R.id.nav_help) {

                        showHelp();
                    }

                    drawerLayout.closeDrawers();

                    return true;
                }
        );
    }

    /**
     * Displays help instructions.
     */
    private void showHelp() {

        new AlertDialog.Builder(this)
                .setTitle(
                        "NASA Image Explorer Help"
                )
                .setMessage(
                        "Use Search to find NASA images.\n\n"
                                + "Save images to Favorites.\n\n"
                                + "Favorites can be viewed later."
                )
                .setPositiveButton(
                        "OK",
                        null
                )
                .show();
    }

    /**
     * Creates the toolbar menu.
     *
     * @param menu toolbar menu
     * @return true when menu is created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater()
                .inflate(
                        R.menu.toolbar_menu,
                        menu
                );

        return true;
    }

    /**
     * Handles toolbar menu selections.
     *
     * @param item selected menu item
     * @return true when item is handled
     */
    @Override
    public boolean onOptionsItemSelected(
            @NonNull MenuItem item
    ) {

        if (item.getItemId() == R.id.menu_help) {

            showHelp();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

