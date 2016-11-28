package com.example.matt1.trackr.api;

/**
 * Created by matt1 on 11/27/2016.
 */

public class ParcelInfo {
    String trackNo;
    String name;
    String desc;

    protected ParcelInfo() {

    }

    public String getId() {
        return trackNo;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String toString() {
        return name;
    }
}
