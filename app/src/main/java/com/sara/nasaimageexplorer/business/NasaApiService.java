package com.sara.nasaimageexplorer.business;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**

 * Business service responsible for communicating
 * with the NASA APOD API.
 *
 * This class keeps API communication outside
 * the Presentation layer.
 *
 * @author Sara
 * @version 8.0
 */
public class NasaApiService {

    private static final String API_KEY =
            "0qhReUQyzRftgLCT4jAgblGqOQXjJPknqEMzgAZC";

    /**

     * Retrieves NASA APOD information for a given date.
     *
     * @param date NASA image date in YYYY-MM-DD format
     * @return JSON response from NASA API, or null if an error occurs
     */
    public String getApodData(String date) {

        HttpURLConnection connection =
                null;

        try {


            URL url =
                    new URL(
                            "https://api.nasa.gov/planetary/apod?api_key="
                                    + API_KEY
                                    + "&date="
                                    + date
                    );

            connection =
                    (HttpURLConnection)
                            url.openConnection();

            connection.setRequestMethod(
                    "GET"
            );

            connection.setConnectTimeout(
                    10000
            );

            connection.setReadTimeout(
                    10000
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

            while ((line =
                    reader.readLine()) != null) {

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

    /**

     * Extracts the NASA image title from API JSON.
     *
     * @param json API JSON response
     * @return image title, or empty string
     */
    public String getTitle(String json) {

        try {


            JSONObject object =
                    new JSONObject(json);

            return object.optString(
                    "title",
                    ""
            );


        } catch (Exception e) {


            return "";


        }
    }

    /**

     * Extracts the standard image URL.
     *
     * @param json API JSON response
     * @return image URL, or empty string
     */
    public String getImageUrl(String json) {

        try {


            JSONObject object =
                    new JSONObject(json);

            return object.optString(
                    "url",
                    ""
            );


        } catch (Exception e) {


            return "";


        }
    }

    /**

     * Extracts the HD image URL.
     *
     * @param json API JSON response
     * @return HD image URL, or empty string
     */
    public String getHdUrl(String json) {

        try {


            JSONObject object =
                    new JSONObject(json);

            return object.optString(
                    "hdurl",
                    ""
            );


        } catch (Exception e) {


            return "";


        }
    }
}
