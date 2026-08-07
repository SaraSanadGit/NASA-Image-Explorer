package com.sara.nasaimageexplorer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.sara.nasaimageexplorer.R;
import com.sara.nasaimageexplorer.api.NasaApiService;
import com.sara.nasaimageexplorer.api.RetrofitClient;
import com.sara.nasaimageexplorer.model.NasaApod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Home Fragment for NASA Image Explorer.
 *
 * @author Sara
 * @version 1.0
 */
public class HomeFragment extends Fragment {

    private static final String API_KEY =
            "0qhReUQyzRftgLCT4jAgblGqOQXjJPknqEMzgAZC";

    private ImageView imgNasa;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvExplanation;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        );


        imgNasa = view.findViewById(R.id.imgNasa);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvDate = view.findViewById(R.id.tvDate);
        tvExplanation = view.findViewById(R.id.tvExplanation);
        progressBar = view.findViewById(R.id.progressBar);


        loadApod();


        return view;
    }


    private void loadApod() {

        progressBar.setVisibility(View.VISIBLE);


        NasaApiService apiService =
                RetrofitClient.getClient()
                        .create(NasaApiService.class);


        Call<NasaApod> call =
                apiService.getPictureOfTheDay(API_KEY);


        call.enqueue(new Callback<NasaApod>() {

            @Override
            public void onResponse(
                    Call<NasaApod> call,
                    Response<NasaApod> response) {


                progressBar.setVisibility(View.GONE);


                if (response.isSuccessful()
                        && response.body() != null) {


                    NasaApod apod = response.body();


                    tvTitle.setText(apod.getTitle());

                    tvDate.setText(apod.getDate());

                    tvExplanation.setText(
                            apod.getExplanation()
                    );


                    if ("image".equals(apod.getMediaType())) {


                        Glide.with(requireContext())
                                .load(apod.getHdUrl())
                                .placeholder(
                                        android.R.drawable.ic_menu_gallery
                                )
                                .into(imgNasa);


                    } else {


                        Toast.makeText(
                                requireContext(),
                                "NASA returned a video today.",
                                Toast.LENGTH_SHORT
                        ).show();

                    }


                } else {


                    Toast.makeText(
                            requireContext(),
                            "Failed to load NASA data.",
                            Toast.LENGTH_SHORT
                    ).show();


                }

            }


            @Override
            public void onFailure(
                    Call<NasaApod> call,
                    Throwable t) {


                progressBar.setVisibility(View.GONE);


                Toast.makeText(
                        requireContext(),
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();

            }

        });

    }

}