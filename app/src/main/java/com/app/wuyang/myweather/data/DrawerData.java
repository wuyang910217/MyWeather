package com.app.wuyang.myweather.data;

/**
 * Created by wuyang on 16-1-1.
 * 左边抽屉式侧边栏的列表项 的实体类；
 */
public class DrawerData {
    private int imageId;
    private int itemName;

    public DrawerData(int imageId,int itemName) {
        this.imageId = imageId;
        this.itemName =itemName;
    }

    public int getImageId() {
        return imageId;
    }

    public int getItemName(){
        return itemName;
    }
}
