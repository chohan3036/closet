package com.example.closet.Clothes;

import java.io.Serializable;

public class ClothesItem implements Serializable {

    private String clothCategory;
    private String clothColor;
    private String clothDescription;
    private int pictureResId;
    private boolean isCheckboxChecked;

    public int getPictureResId() {
        return pictureResId;
    }

    public void setPictureResId(int pictureResId) {
        this.pictureResId = pictureResId;
    }

    //

    public String getClothCategory() {
        return clothCategory;
    }

    public void setClothCategory(String clothCategory) {
        this.clothCategory = clothCategory;
    }

    //

    public String getClothColor() {
        return clothColor;
    }

    public void setClothColor(String clothColor) {
        this.clothColor = clothColor;
    }

    //

    public String getClothDescription() {
        return clothDescription;
    }

    public void setClothDescription(String clothDescription) {
        this.clothDescription = clothDescription;
    }

    //

    public boolean isCheckboxChecked() {
        return isCheckboxChecked;
    }

    public void setCheckboxChecked(boolean isCheckboxChecked) {
        this.isCheckboxChecked = isCheckboxChecked;
    }

}
