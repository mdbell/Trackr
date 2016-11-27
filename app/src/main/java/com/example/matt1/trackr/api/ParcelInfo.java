package com.example.matt1.trackr.api;

import java.util.Arrays;

/**
 * Created by matt1 on 11/27/2016.
 */

public class ParcelInfo {
    String uid;
    long id;
    String name;
    String desc;
    ParcelRow[] rows;

    //TODO remove
    public ParcelInfo(String uid, long id, String name, String desc, ParcelRow[] rows) {
        this.uid = uid;
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.rows = rows;
    }

    protected ParcelInfo() {

    }

    public String getUid() {
        return uid;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public ParcelRow[] getRows() {
        if (rows == null) {
            return null;
        }
        //We do this to make the array immutable
        return Arrays.copyOf(rows, rows.length);
    }

    public String toString() {
        return name + " - " + id;
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
