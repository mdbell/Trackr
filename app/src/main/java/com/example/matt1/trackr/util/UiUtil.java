package com.example.matt1.trackr.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by matt1 on 11/26/2016.
 */

public class UiUtil {

    static final DialogInterface.OnClickListener DISMESS_DIALOG = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    };
    private static final DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");

    public static AlertDialog alert(Context ctx, String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(ctx).create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", DISMESS_DIALOG);
        return dialog;
    }

    public static Toast toast(Context ctx, String message) {
        return Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
    }

    public static String formatTimestamp(long timestamp) {
        return formatter.format(new Date(timestamp * 1000));
    }

}
