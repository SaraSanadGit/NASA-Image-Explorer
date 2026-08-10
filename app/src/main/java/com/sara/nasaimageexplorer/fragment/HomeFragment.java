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
 * Home Fragment.
 *
 * Displays NASA Astronomy Picture of the Day.
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

            @Nullable Bundle savedInstanceState

    ) {



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





        loadImage();





        return view;



    }







    private void loadImage(){



        progressBar.setVisibility(View.VISIBLE);




        NasaApiService service =

                RetrofitClient.getClient()

                        .create(NasaApiService.class);





        Call<NasaApod> call =

                service.getPictureOfTheDay(

                        API_KEY,

                        ""

                );






        call.enqueue(new Callback<NasaApod>() {



            @Override

            public void onResponse(

                    Call<NasaApod> call,

                    Response<NasaApod> response

            ){



                progressBar.setVisibility(

                        View.GONE

                );





                if(response.isSuccessful()

                        && response.body()!=null){



                    NasaApod apod = response.body();




                    tvTitle.setText(

                            apod.getTitle()

                    );




                    tvDate.setText(

                            apod.getDate()

                    );




                    tvExplanation.setText(

                            apod.getExplanation()

                    );





                    Glide.with(requireContext())

                            .load(apod.getUrl())

                            .into(imgNasa);



                }

                else {



                    Toast.makeText(

                            requireContext(),

                            "Failed loading image",

                            Toast.LENGTH_SHORT

                    ).show();



                }



            }





            @Override

            public void onFailure(

                    Call<NasaApod> call,

                    Throwable t

            ){



                progressBar.setVisibility(

                        View.GONE

                );



                Toast.makeText(

                        requireContext(),

                        t.getMessage(),

                        Toast.LENGTH_LONG

                ).show();



            }



        });



    }



}