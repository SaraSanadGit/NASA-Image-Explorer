package com.sara.nasaimageexplorer.model;


/**
 * Model class representing a favorite NASA image.
 *
 * This class stores the information retrieved from the
 * FavoriteImages database table.
 *
 * @author Sara
 * @version 1.0
 */
public class FavoriteImage {

    private int id;
    private String date;
    private String title;
    private String imageUrl;
    private String hdUrl;


    /**
     * Creates a FavoriteImage object.
     *
     * @param id database ID
     * @param date date of the NASA image
     * @param title title of the NASA image
     * @param imageUrl URL of the NASA image
     * @param hdUrl HD URL of the NASA image
     */
    public FavoriteImage(
            int id,
            String date,
            String title,
            String imageUrl,
            String hdUrl
    ) {

        this.id = id;
        this.date = date;
        this.title = title;
        this.imageUrl = imageUrl;
        this.hdUrl = hdUrl;
    }


    /**
     * Returns the database ID.
     *
     * @return favorite image ID
     */
    public int getId() {
        return id;
    }


    /**
     * Returns the NASA image date.
     *
     * @return image date
     */
    public String getDate() {
        return date;
    }


    /**
     * Returns the NASA image title.
     *
     * @return image title
     */
    public String getTitle() {
        return title;
    }


    /**
     * Returns the NASA image URL.
     *
     * @return image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }


    /**
     * Returns the HD NASA image URL.
     *
     * @return HD image URL
     */
    public String getHdUrl() {
        return hdUrl;
    }
}