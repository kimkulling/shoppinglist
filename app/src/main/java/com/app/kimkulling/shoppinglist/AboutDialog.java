package com.app.kimkulling.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kimku_000 on 29.03.2016.
 */
public class AboutDialog {
    public static void show( Activity parentActivity ) {
        SpannableString aboutText = new SpannableString( "Version "
                + VersionInfo.version( parentActivity )
                +"\n\n" + parentActivity.getString( R.string.dlg_text_about ) );
        View about;
        TextView tvAbout;
        try {
            LayoutInflater inflater = parentActivity.getLayoutInflater();
            about = inflater.inflate( R.layout.dlg_about,
                    (ViewGroup) parentActivity.findViewById( R.id.aboutView ) );
            tvAbout = (TextView) about.findViewById( R.id.aboutText );
        } catch ( InflateException e ) {
            about = tvAbout = new TextView( parentActivity );
        }

        tvAbout.setText( aboutText );
        Linkify.addLinks( tvAbout, Linkify.ALL );
        // build and show the about dialog

        new AlertDialog.Builder( parentActivity )
                .setTitle("About")
                .setCancelable( true )
                .setIcon( null )
                .setPositiveButton( "OK", null )
                .setView( about )
                .show();
    }
}