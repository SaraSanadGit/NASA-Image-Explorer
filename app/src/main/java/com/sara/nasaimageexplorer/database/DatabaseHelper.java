package com.sara.nasaimageexplorer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * SQLite Database Helper.
 *
 * Creates and manages the database used to store
 * favorite NASA images.
 *
 * @author Sara
 * @version 2.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME =
            "NASA_Favorites.db";

    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME =
            "FavoriteImages";

    public static final String COLUMN_ID =
            "id";

    public static final String COLUMN_DATE =
            "date";

    public static final String COLUMN_TITLE =
            "title";

    public static final String COLUMN_IMAGE_URL =
            "imageUrl";

    public static final String COLUMN_HD_URL =
            "hdUrl";


    /**
     * Creates the database helper.
     *
     * @param context application context
     */
    public DatabaseHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }


    /**
     * Creates the FavoriteImages table.
     *
     * @param db database instance
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_DATE + " TEXT NOT NULL, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
                        COLUMN_HD_URL + " TEXT" +
                        ")";

        db.execSQL(createTable);
    }


    /**
     * Upgrades the database from an older version.
     *
     * Version 2 adds support for storing the NASA HD image URL.
     *
     * @param db database instance
     * @param oldVersion previous database version
     * @param newVersion new database version
     */
    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        if (oldVersion < 2) {

            db.execSQL(
                    "ALTER TABLE " +
                            TABLE_NAME +
                            " ADD COLUMN " +
                            COLUMN_HD_URL +
                            " TEXT"
            );
        }
    }


    /**
     * Adds a NASA image to the favorites database.
     *
     * @param date date of the NASA image
     * @param title title of the NASA image
     * @param imageUrl standard image URL
     * @param hdUrl high-definition image URL
     * @return true if the image was added successfully
     */
    public boolean addFavorite(
            String date,
            String title,
            String imageUrl,
            String hdUrl
    ) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_DATE,
                date
        );

        values.put(
                COLUMN_TITLE,
                title
        );

        values.put(
                COLUMN_IMAGE_URL,
                imageUrl
        );

        values.put(
                COLUMN_HD_URL,
                hdUrl
        );

        long result =
                db.insert(
                        TABLE_NAME,
                        null,
                        values
                );

        return result != -1;
    }
}