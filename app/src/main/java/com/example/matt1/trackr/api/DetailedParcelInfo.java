package com.example.matt1.trackr.api;

import java.util.Arrays;

/**
 * Created by matt1 on 11/27/2016.
 */

public class DetailedParcelInfo extends ParcelInfo {

    ParcelRow[] rows;

    protected DetailedParcelInfo() {

    }

    public ParcelRow[] getRows() {
        if (rows == null) {
            return null;
        }
        //We do this to make the array immutable
        return Arrays.copyOf(rows, rows.length);
    }

    public static class ParcelRow {
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
