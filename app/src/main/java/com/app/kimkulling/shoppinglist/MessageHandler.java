package com.app.kimkulling.shoppinglist;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

/**
 * Created by kimku_000 on 24.06.2016.
 */
public class MessageHandler {
    private static final String TAG = "MessageHandler";
    private static MessageHandler sMessageHandler = null;

    private MessageHandler() {
        // empty
    }

    public static void create(CoordinatorLayout view) {
        if ( null == sMessageHandler ) {
            sMessageHandler = new MessageHandler();
        }
    }

    public static MessageHandler instance() {
        return sMessageHandler;
    }

    public void showMessage(View v, final String message ) {
        if ( 0 == message.length() ) {
            return;
        }

        if ( null != v ) {
            Snackbar snackbar = Snackbar.make( v, message, Snackbar.LENGTH_LONG );
            snackbar.show();
        } else {
            Log.d( TAG, "No view assigned to message handler." );
        }
        Log.i( TAG, message );
    }

    public void showMessage(View v, final int messageId ) {
        if ( -1 == messageId ) {
            return;
        }

        if ( null != v ) {
            Snackbar snackbar = Snackbar.make( v, messageId, Snackbar.LENGTH_LONG );
            snackbar.show();
        } else {
            Log.d( TAG, "No view assigned to message handler." );
        }
    }
}
