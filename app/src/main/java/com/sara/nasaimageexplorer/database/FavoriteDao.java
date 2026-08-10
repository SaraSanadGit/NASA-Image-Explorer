package com.sara.nasaimageexplorer.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sara.nasaimageexplorer.model.FavoriteImage;

import java.util.ArrayList;


/**
 * Data Access Object for Favorite Images.
 *
 * Provides database operations for adding,
 * retrieving, and deleting favorite NASA images.
 *
 * @author Sara
 * @version 2.0
 */
public class FavoriteDao {

    private final DatabaseHelper databaseHelper;


    /**
     * Creates a FavoriteDao.
     *
     * @param databaseHelper database helper used for SQLite access
     */
    public FavoriteDao(DatabaseHelper databaseHelper) {

        this.databaseHelper = databaseHelper;
    }


    /**
     * Adds a NASA image to the favorites database.
     *
     * @param date NASA image date
     * @param title NASA image title
     * @param imageUrl standard image URL
     * @param hdUrl high-definition image URL
     * @return true when the image is successfully inserted
     */
    public boolean addFavorite(
            String date,
            String title,
            String imageUrl,
            String hdUrl
    ) {

        SQLiteDatabase db =
                databaseHelper.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                DatabaseHelper.COLUMN_DATE,
                date
        );

        values.put(
                DatabaseHelper.COLUMN_TITLE,
                title
        );

        values.put(
                DatabaseHelper.COLUMN_IMAGE_URL,
                imageUrl
        );

        values.put(
                DatabaseHelper.COLUMN_HD_URL,
                hdUrl
        );

        long result =
                db.insert(
                        DatabaseHelper.TABLE_NAME,
                        null,
                        values
                );

        return result != -1;
    }


    /**
     * Retrieves all favorite NASA images.
     *
     * @return list containing all saved favorite images
     */
    public ArrayList<FavoriteImage> getFavorites() {

        ArrayList<FavoriteImage> list =
                new ArrayList<>();

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor cursor =
                db.query(
                        DatabaseHelper.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        DatabaseHelper.COLUMN_ID + " DESC"
                );


        while (cursor.moveToNext()) {

            int id =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_ID
                            )
                    );

            String date =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_DATE
                            )
                    );

            String title =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_TITLE
                            )
                    );

            String imageUrl =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_IMAGE_URL
                            )
                    );

            String hdUrl =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_HD_URL
                            )
                    );


            FavoriteImage image =
                    new FavoriteImage(
                            id,
                            date,
                            title,
                            imageUrl,
                            hdUrl
                    );

            list.add(image);
        }


        cursor.close();

        return list;
    }


    /**
     * Retrieves one favorite NASA image by its database ID.
     *
     * @param id database ID of the favorite
     * @return FavoriteImage object, or null when not found
     */
    public FavoriteImage getFavoriteById(int id) {

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor cursor =
                db.query(
                        DatabaseHelper.TABLE_NAME,
                        null,
                        DatabaseHelper.COLUMN_ID + "=?",
                        new String[]{
                                String.valueOf(id)
                        },
                        null,
                        null,
                        null
                );

        FavoriteImage image = null;


        if (cursor.moveToFirst()) {

            String date =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_DATE
                            )
                    );

            String title =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_TITLE
                            )
                    );

            String imageUrl =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_IMAGE_URL
                            )
                    );

            String hdUrl =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_HD_URL
                            )
                    );


            image =
                    new FavoriteImage(
                            id,
                            date,
                            title,
                            imageUrl,
                            hdUrl
                    );
        }


        cursor.close();

        return image;
    }


    /**
     * Deletes a favorite NASA image.
     *
     * @param id database ID of the favorite to delete
     * @return true when an item was deleted
     */
    public boolean deleteFavorite(int id) {

        SQLiteDatabase db =
                databaseHelper.getWritableDatabase();

        int rowsDeleted =
                db.delete(
                        DatabaseHelper.TABLE_NAME,
                        DatabaseHelper.COLUMN_ID + "=?",
                        new String[]{
                                String.valueOf(id)
                        }
                );

        return rowsDeleted > 0;
    }
}