package com.app.wuyang.myweather.data;

/**
 * Created by wuyang on 16-1-7.
 */
public class AllCity {
    private int id;
    private String name;

    public AllCity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AllCity() {
        super();
    }

    @Override
    public String toString() {
        return "AllCity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
