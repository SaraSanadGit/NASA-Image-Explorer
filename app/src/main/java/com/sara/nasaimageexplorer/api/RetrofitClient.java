package com.sara.nasaimageexplorer.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;





/**
 * Creates Retrofit API client.
 *
 * @author Sara
 * @version 1.0
 */
public class RetrofitClient {



    private static final String BASE_URL =

            "https://api.nasa.gov/";



    private static Retrofit retrofit;







    public static Retrofit getClient(){



        if(retrofit == null){



            retrofit = new Retrofit.Builder()

                    .baseUrl(BASE_URL)

                    .addConverterFactory(

                            GsonConverterFactory.create()

                    )

                    .build();



        }



        return retrofit;



    }



}