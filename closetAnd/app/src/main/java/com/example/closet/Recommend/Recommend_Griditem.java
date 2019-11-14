package com.example.closet.Recommend;

public class Recommend_Griditem {

    String style;
    int imgno;

    public Recommend_Griditem(String style, int imgno){
        this.style=style;
        this.imgno=imgno;

    }

    public String getStyle(){return style;}
    public void setStyle(){this.style=style;}

    public int getImgno() {return imgno;}
    public void setImgno(int imgno){this.imgno=imgno;}
}
