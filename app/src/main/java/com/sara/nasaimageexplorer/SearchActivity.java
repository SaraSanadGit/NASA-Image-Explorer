package com.sara.nasaimageexplorer;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.sara.nasaimageexplorer.database.DatabaseHelper;
import com.sara.nasaimageexplorer.database.FavoriteDao;
import com.sara.nasaimageexplorer.utils.SharedPreferencesManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**

 * Search Activity.
 *
 * Allows users to search NASA images by date.
 * Uses AsyncTask for HTTP request.
 *
 * @author Sara
 * @version 5.0
 */
public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Button btnDate;

    private Button btnSave;

    private ImageView imgResult;

    private TextView tvResultTitle;

    private TextView tvResultDate;

    private TextView tvUrl;

    private ProgressBar progressBar;

    private String selectedDate = "";

    private String imageUrl = "";

    private String imageHdUrl = "";

    private String imageTitle = "";

    private DatabaseHelper databaseHelper;

    private FavoriteDao favoriteDao;

    private SharedPreferencesManager preferencesManager;

    private static final String API_KEY =
            "0qhReUQyzRftgLCT4jAgblGqOQXjJPknqEMzgAZC";

    /**

     * Creates the Search Activity.
     *
     * @param savedInstanceState saved activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        toolbar =
                findViewById(
                        R.id.toolbarSearch
                );

        setSupportActionBar(toolbar);

        btnDate =
                findViewById(
                        R.id.btnDate
                );

        btnSave =
                findViewById(
                        R.id.btnSave
                );

        imgResult =
                findViewById(
                        R.id.imgResult
                );

        tvResultTitle =
                findViewById(
                        R.id.tvResultTitle
                );

        tvResultDate =
                findViewById(
                        R.id.tvResultDate
                );

        tvUrl =
                findViewById(
                        R.id.tvUrl
                );

        progressBar =
                findViewById(
                        R.id.progressBar
                );

        databaseHelper =
                new DatabaseHelper(this);

        favoriteDao =
                new FavoriteDao(databaseHelper);

        preferencesManager =
                new SharedPreferencesManager(this);

        btnDate.setOnClickListener(
                v -> showDatePicker()
        );

        btnSave.setOnClickListener(
                v -> saveFavorite(v)
        );
    }

    /**

     * Creates the toolbar menu.
     *
     * @param menu options menu
     * @return true when the menu is created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.toolbar_menu,
                menu
        );

        return true;
    }

    /**

     * Handles toolbar menu selections.
     *
     * @param item selected menu item
     * @return true when the item is handled
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

    /**

     * Displays the Help dialog.
     */
    private void showHelp() {

        new AlertDialog.Builder(this)
                .setTitle(
                        "NASA Image Explorer Help"
                )
                .setMessage(
                        "Use Select Date to search for a NASA image.\n\n"
                                + "The selected image can be saved "
                                + "to Favorites.\n\n"
                                + "Use the Help menu for application "
                                + "instructions."
                )
                .setPositiveButton(
                        "OK",
                        null
                )
                .show();
    }

    /**

     * Saves the currently displayed image.
     *
     * @param view button view
     */
    private void saveFavorite(View view) {

        if (selectedDate.isEmpty()
                || imageTitle.isEmpty()
                || imageUrl.isEmpty()) {


            Toast.makeText(
                    this,
                    "Please search for an image first",
                    Toast.LENGTH_SHORT
            ).show();

            return;


        }

        boolean saved =
                favoriteDao.addFavorite(
                        selectedDate,
                        imageTitle,
                        imageUrl,
                        imageHdUrl
                );

        if (saved) {


            Snackbar.make(
                    view,
                    "Saved to Favorites",
                    Snackbar.LENGTH_LONG
            ).show();


        } else {


            Toast.makeText(
                    this,
                    "Save failed",
                    Toast.LENGTH_SHORT
            ).show();


        }
    }

    /**

     * Displays the date picker.
     */
    private void showDatePicker() {

        Calendar calendar =
                Calendar.getInstance();

        DatePickerDialog dialog =
                new DatePickerDialog(
                        this,
                        (
                                DatePicker view,
                                int year,
                                int month,
                                int day
                        ) -> {


                            selectedDate =
                                    year
                                            + "-"
                                            + String.format(
                                            "%02d",
                                            month + 1
                                    )
                                            + "-"
                                            + String.format(
                                            "%02d",
                                            day
                                    );

                            preferencesManager.saveLastDate(
                                    selectedDate
                            );

                            new NasaTask().execute(
                                    selectedDate
                            );
                        },
                        calendar.get(
                                Calendar.YEAR
                        ),
                        calendar.get(
                                Calendar.MONTH
                        ),
                        calendar.get(
                                Calendar.DAY_OF_MONTH
                        )
                );


        dialog.show();
    }

    /**

     * Performs the NASA API request.
     */
    private class NasaTask
            extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {


            progressBar.setVisibility(
                    View.VISIBLE
            );

            btnSave.setEnabled(false);


        }

        @Override
        protected String doInBackground(
                String... dates
        ) {


            HttpURLConnection connection = null;

            try {

                URL url =
                        new URL(
                                "https://api.nasa.gov/planetary/apod?api_key="
                                        + API_KEY
                                        + "&date="
                                        + dates[0]
                        );

                connection =
                        (HttpURLConnection)
                                url.openConnection();

                connection.setRequestMethod(
                        "GET"
                );

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        connection.getInputStream()
                                )
                        );

                StringBuilder result =
                        new StringBuilder();

                String line;

                while (
                        (line = reader.readLine())
                                != null
                ) {

                    result.append(line);
                }

                reader.close();

                return result.toString();

            } catch (Exception e) {

                return null;

            } finally {

                if (connection != null) {

                    connection.disconnect();
                }
            }


        }

        @Override
        protected void onPostExecute(
                String result
        ) {


            progressBar.setVisibility(
                    View.GONE
            );

            btnSave.setEnabled(true);

            if (result == null) {

                Toast.makeText(
                        SearchActivity.this,
                        "Error loading data",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            try {

                JSONObject object =
                        new JSONObject(result);

                imageTitle =
                        object.getString(
                                "title"
                        );

                imageUrl =
                        object.getString(
                                "url"
                        );

                imageHdUrl =
                        object.optString(
                                "hdurl",
                                ""
                        );

                tvResultTitle.setText(
                        imageTitle
                );

                tvResultDate.setText(
                        selectedDate
                );

                tvUrl.setText(
                        imageUrl
                );

                Glide.with(
                                SearchActivity.this
                        )
                        .load(imageUrl)
                        .into(imgResult);

            } catch (Exception e) {

                Toast.makeText(
                        SearchActivity.this,
                        "Error loading data",
                        Toast.LENGTH_SHORT
                ).show();
            }


        }
    }
}
