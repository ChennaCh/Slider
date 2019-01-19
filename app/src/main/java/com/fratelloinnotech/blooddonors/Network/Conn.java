package com.fratelloinnotech.blooddonors.Network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by dell on 2/15/2018.
 */

public class Conn  {
    static Context c;

    public static AlertDialog displayMobileDataSettingsDialog(final Context context){
         c=context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Network Connection");
        //builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
//                startActivity(intent);
                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                return;
               // finish();

            }
        });
        builder.show();

        return builder.create();
    }

}
