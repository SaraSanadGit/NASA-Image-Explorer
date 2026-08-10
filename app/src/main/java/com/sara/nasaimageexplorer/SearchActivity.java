package com.sara.nasaimageexplorer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.sara.nasaimageexplorer.business.FavoriteService;
import com.sara.nasaimageexplorer.business.NasaApiService;
import com.sara.nasaimageexplorer.database.DatabaseHelper;
import com.sara.nasaimageexplorer.database.FavoriteDao;
import com.sara.nasaimageexplorer.utils.SharedPreferencesManager;

import java.util.Calendar;

/**

 * Search Activity.
 *
 * Presentation layer responsible for displaying
 * NASA image search results and handling user actions.
 *
 * Business operations are handled by service classes.
 *
 * @author Sara
 * @version 8.1
 */
public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbarSearch;

    private Button btnDate;

    private Button btnSearch;

    private Button btnSave;

    private EditText etDate;

    private ImageView imgResult;

    private TextView tvResultTitle;

    private TextView tvResultDate;

    private TextView tvUrl;

    private TextView tvHdUrl;

    private ProgressBar progressBar;

    private String selectedDate = "";

    private String imageUrl = "";

    private String imageTitle = "";

    private String hdUrl = "";

    private DatabaseHelper databaseHelper;

    private FavoriteDao favoriteDao;

    private FavoriteService favoriteService;

    private SharedPreferencesManager preferencesManager;

    private NasaApiService nasaApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        toolbarSearch =
                findViewById(R.id.toolbarSearch);

        btnDate =
                findViewById(R.id.btnDate);

        btnSearch =
                findViewById(R.id.btnSearch);

        btnSave =
                findViewById(R.id.btnSave);

        etDate =
                findViewById(R.id.etDate);

        imgResult =
                findViewById(R.id.imgResult);

        tvResultTitle =
                findViewById(R.id.tvResultTitle);

        tvResultDate =
                findViewById(R.id.tvResultDate);

        tvUrl =
                findViewById(R.id.tvUrl);

        tvHdUrl =
                findViewById(R.id.tvHdUrl);

        progressBar =
                findViewById(R.id.progressBar);

        databaseHelper =
                new DatabaseHelper(this);

        favoriteDao =
                new FavoriteDao(databaseHelper);

        favoriteService =
                new FavoriteService(favoriteDao);

        preferencesManager =
                new SharedPreferencesManager(this);

        nasaApiService =
                new NasaApiService();

        setSupportActionBar(toolbarSearch);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle(
                    "Search NASA Image"
            );
        }

        btnDate.setOnClickListener(
                v -> showDatePicker()
        );

        btnSearch.setOnClickListener(
                v -> searchByEnteredDate()
        );

        btnSave.setOnClickListener(
                v -> saveFavorite(v)
        );

        tvUrl.setOnClickListener(
                v -> openUrl(imageUrl)
        );

        tvHdUrl.setOnClickListener(
                v -> openUrl(hdUrl)
        );


    }

    /**

     * Opens the Android date picker.
     */
    private void showDatePicker() {

        Calendar calendar =
                Calendar.getInstance();

        DatePickerDialog dialog =
                new DatePickerDialog(
                        this,
                        (view, year, month, day) -> {


                            selectedDate =
                                    year + "-" +
                                            String.format(
                                                    "%02d",
                                                    month + 1
                                            ) +
                                            "-" +
                                            String.format(
                                                    "%02d",
                                                    day
                                            );

                            etDate.setText(
                                    selectedDate
                            );

                            preferencesManager.saveLastDate(
                                    selectedDate
                            );

                            new NasaTask().execute(
                                    selectedDate
                            );
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );


        dialog.show();
    }

    /**

     * Searches NASA APOD using the date
     * entered into the EditText.
     */
    private void searchByEnteredDate() {

        String enteredDate =
                etDate.getText()
                        .toString()
                        .trim();

        if (enteredDate.isEmpty()) {


            etDate.setError(
                    "Enter a date"
            );

            etDate.requestFocus();

            return;


        }

        if (!enteredDate.matches(
                "\\d{4}-\\d{2}-\\d{2}"
        )) {


            etDate.setError(
                    "Use YYYY-MM-DD format"
            );

            etDate.requestFocus();

            return;


        }

        selectedDate =
                enteredDate;

        preferencesManager.saveLastDate(
                selectedDate
        );

        new NasaTask().execute(
                selectedDate
        );
    }

    /**

     * Saves the currently displayed image
     * as a favorite through the Business layer.
     *
     * @param view clicked view
     */
    private void saveFavorite(View view) {

        if (selectedDate.isEmpty()
                || imageTitle.isEmpty()
                || imageUrl.isEmpty()) {


            Toast.makeText(
                    this,
                    "Search for an image first",
                    Toast.LENGTH_SHORT
            ).show();

            return;


        }

        if (favoriteService.addFavorite(
                selectedDate,
                imageTitle,
                imageUrl,
                hdUrl
        )) {


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

     * Opens a URL in the Android browser.
     *
     * @param urlString URL to open
     */
    private void openUrl(String urlString) {

        if (urlString == null
                || urlString.isEmpty()) {


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

            startActivity(browserIntent);


        } catch (Exception e) {


            Toast.makeText(
                    this,
                    "Unable to open URL",
                    Toast.LENGTH_SHORT
            ).show();


        }
    }

    /**

     * Performs the NASA API request
     * through the Business layer.
     */
    private class NasaTask
            extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {


            progressBar.setVisibility(
                    View.VISIBLE
            );


        }

        @Override
        protected String doInBackground(
                String... dates
        ) {


            return nasaApiService.getApodData(
                    dates[0]
            );


        }

        @Override
        protected void onPostExecute(
                String result
        ) {


            progressBar.setVisibility(
                    View.GONE
            );

            if (result == null
                    || result.isEmpty()) {

                Toast.makeText(
                        SearchActivity.this,
                        "Error loading NASA data",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            try {

                imageTitle =
                        nasaApiService.getTitle(
                                result
                        );

                imageUrl =
                        nasaApiService.getImageUrl(
                                result
                        );

                hdUrl =
                        nasaApiService.getHdUrl(
                                result
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

                if (hdUrl != null
                        && !hdUrl.isEmpty()) {

                    tvHdUrl.setText(
                            hdUrl
                    );

                } else {

                    tvHdUrl.setText(
                            "HD image not available"
                    );
                }

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
