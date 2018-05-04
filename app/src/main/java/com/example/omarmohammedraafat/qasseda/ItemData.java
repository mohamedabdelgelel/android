package com.example.omarmohammedraafat.qasseda;

import java.io.Serializable;

/**
 * Created by OmarMohammedRaafat on 11/25/2017.
 */

public class ItemData implements Serializable {

    private String Description;
    private String imageResource;
    private String department;


    public ItemData(String description, String imageResource, String department) {
        Description = description;
        this.imageResource = imageResource;
        this.department = department;
    }

    public ItemData() {
    }

    public String getDescription() {
        return Description;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
