package com.sara.nasaimageexplorer.business;

import com.sara.nasaimageexplorer.database.FavoriteDao;
import com.sara.nasaimageexplorer.model.FavoriteImage;

import java.util.ArrayList;

/**

 * Business service for managing favorite NASA images.
 *
 * This class acts as the Business layer between
 * the Presentation layer and the Data Access layer.
 *
 * @author Sara
 * @version 8.0
 */
public class FavoriteService {

    private final FavoriteDao favoriteDao;

    /**

     * Creates a FavoriteService.
     *
     * @param favoriteDao DAO used for database operations
     */
    public FavoriteService(FavoriteDao favoriteDao) {

        this.favoriteDao = favoriteDao;
    }

    /**

     * Adds a favorite NASA image.
     *
     * @param date NASA image date
     * @param title NASA image title
     * @param imageUrl standard image URL
     * @param hdUrl high-definition image URL
     * @return true when the image is saved successfully
     */
    public boolean addFavorite(
            String date,
            String title,
            String imageUrl,
            String hdUrl
    ) {

        return favoriteDao.addFavorite(
                date,
                title,
                imageUrl,
                hdUrl
        );
    }

    /**

     * Retrieves all favorite NASA images.
     *
     * @return list of favorite images
     */
    public ArrayList<FavoriteImage> getFavorites() {

        return favoriteDao.getFavorites();
    }

    /**

     * Retrieves one favorite image by its ID.
     *
     * @param id database ID
     * @return favorite image or null
     */
    public FavoriteImage getFavoriteById(int id) {

        return favoriteDao.getFavoriteById(id);
    }

    /**

     * Deletes a favorite NASA image.
     *
     * @param id database ID of the favorite
     * @return true when the favorite is deleted
     */
    public boolean deleteFavorite(int id) {

        return favoriteDao.deleteFavorite(id);
    }
}
