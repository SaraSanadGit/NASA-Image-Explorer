package com.sara.nasaimageexplorer.model;


/**
 * Model class for NASA APOD API response.
 *
 * @author Sara
 * @version 1.0
 */
public class NasaApod {



    private String title;

    private String date;

    private String explanation;

    private String url;

    private String hdurl;







    public String getTitle(){

        return title;

    }





    public void setTitle(String title){

        this.title = title;

    }






    public String getDate(){

        return date;

    }





    public void setDate(String date){

        this.date = date;

    }







    public String getExplanation(){

        return explanation;

    }





    public void setExplanation(String explanation){

        this.explanation = explanation;

    }







    public String getUrl(){

        return url;

    }





    public void setUrl(String url){

        this.url = url;

    }







    public String getHdUrl(){

        return hdurl;

    }





    public void setHdUrl(String hdurl){

        this.hdurl = hdurl;

    }



}