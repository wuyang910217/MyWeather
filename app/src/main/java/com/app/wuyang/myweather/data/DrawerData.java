package com.app.wuyang.myweather.data;

/**
 * Created by wuyang on 16-1-1.
 */
public class DrawerData {
    private int imageId;
    private String itemName;

    public DrawerData(int imageId,String itemName) {
        this.imageId = imageId;
        this.itemName =itemName;
    }

    public int getImageId() {
        return imageId;
    }

    public String getItemName(){
        return itemName;
    }
}
