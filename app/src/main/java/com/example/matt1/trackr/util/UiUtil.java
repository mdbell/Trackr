package com.example.matt1.trackr.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

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

    public static AlertDialog alert(Context ctx, String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(ctx).create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", DISMESS_DIALOG);
        return dialog;
    }

}
