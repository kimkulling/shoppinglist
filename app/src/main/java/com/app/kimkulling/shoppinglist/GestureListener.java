package com.app.kimkulling.shoppinglist;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by kimku_000 on 09.04.2016.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final String TAG = "GestureListener";
    private Activity mParentActivity;
    private ListView mLV;
    private DatabaseAccess mDBAccess;

    public GestureListener( Activity parentActivity, ListView lv, DatabaseAccess dbAccess ) {
        super();

        mParentActivity = parentActivity;
        mLV = lv;
        mDBAccess = dbAccess;
    }

    @Override
    public boolean onDown( MotionEvent event ) {
        Log.d(TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {
        Log.d(TAG, "onFling: " + e1.toString() + e2.toString());
        final int position = mLV.pointToPosition( Math.round(e1.getX()), Math.round(e1.getY()));
        final String shop = (String) mLV.getItemAtPosition( position );
        boolean result = false;
        if ( mDBAccess.hasShoppingList( shop ) ) {
            mDBAccess.deleteShoppingList( shop );
            result = true;
        }

        return result;
    }
}
