package com.alper.pola.andoid.grapesnberries;

import java.io.Serializable;

/**
 * Created by pola alper on 28-Aug-16.
 */
public class itemsmodel implements Serializable {




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    private String title;
    private String description;
    private String image;










}
