package com.sara.nasaimageexplorer.utils;


import android.content.Context;
import android.content.SharedPreferences;





/**
 * Handles application SharedPreferences.
 *
 * Saves last searched date.
 *
 * @author Sara
 * @version 1.0
 */
public class SharedPreferencesManager {



    private static final String PREF_NAME =
            "NASA_PREFS";


    private static final String LAST_DATE =
            "last_search_date";



    private SharedPreferences preferences;






    public SharedPreferencesManager(Context context){



        preferences =

                context.getSharedPreferences(

                        PREF_NAME,

                        Context.MODE_PRIVATE

                );



    }






    public void saveLastDate(String date){



        preferences.edit()

                .putString(

                        LAST_DATE,

                        date

                )

                .apply();



    }






    public String getLastDate(){



        return preferences.getString(

                LAST_DATE,

                ""

        );



    }



}