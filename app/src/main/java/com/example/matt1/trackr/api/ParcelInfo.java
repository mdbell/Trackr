package com.example.matt1.trackr.api;

/**
 * Created by matt1 on 11/27/2016.
 */

public class ParcelInfo {
    String trackNo;
    String name;
    String desc;

    //TODO remove
    public ParcelInfo(String trackNo, String name, String desc) {
        this.trackNo = trackNo;
        this.name = name;
        this.desc = desc;
    }

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

    static class ParcelRow {
        long time;
        String location;
        String status;

        protected ParcelRow() {

        }

        public long getTime() {
            return time;
        }

        public String getLocation() {
            return location;
        }

        public String getStatus() {
            return status;
        }
    }
}
