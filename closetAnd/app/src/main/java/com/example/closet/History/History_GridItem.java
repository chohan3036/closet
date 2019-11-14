package com.example.closet.History;

public class History_GridItem {

    String style;
    int imgno;

    public History_GridItem(String style, int imgno) {
        this.style = style;
        this.imgno = imgno;
    }

    public String getStyle() { return style; }
    public void setStyle() { this.style = style; }

    public int getImgno() {
        return imgno;
    }
    public void setImage(int imgno) { this.imgno = imgno; }

}