package com.sara.nasaimageexplorer.api;

import com.sara.nasaimageexplorer.model.NasaApod;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * NASA API Service.
 *
 * @author Sara
 * @version 1.0
 */
public interface NasaApiService {

    @GET("planetary/apod")
    Call<NasaApod> getPictureOfTheDay(

            @Query("api_key") String apiKey

    );
}