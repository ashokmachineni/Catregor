package com.ashok.catregor;

import java.io.Serializable;

/**
 * Created by ashok on 10/12/16.
 */

public class Blog implements Serializable {
    private String title,image,link,category;
    public Blog(){}


    public Blog(String image, String title, String link, String category) {
        this.image = image;
        this.title = title;
        this.link = link;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
