package com.app.kimkulling.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by kimku_000 on 09.04.2016.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener  implements
        View.OnTouchListener {
    private static final String TAG = "GestureListener";
    private Activity mParentActivity;
    private ListView mLV;
    private GestureDetectorCompat mGestureDetector;
    private ShoppingListControl mShoppingListControl;

    public GestureListener( Activity parentActivity, ListView lv, ShoppingListControl control ) {
        super();

        mParentActivity = parentActivity;
        mLV = lv;
        mGestureDetector = null;
        mShoppingListControl = control;
    }

    public void setGestureDetector( GestureDetectorCompat gd ) {
        mGestureDetector = gd;
    }

    @Override
    public boolean onDown( MotionEvent event ) {
        Log.d(TAG,"onDown: " + event.toString());
        final int position = mLV.pointToPosition( Math.round(event.getX()), Math.round(event.getY()));
        final String shop = (String) mLV.getItemAtPosition( position );
        boolean result = false;
        if ( mShoppingListControl.getDatabaseAccess().hasShoppingList( shop ) ) {
            Log.d( TAG, "clicked on" + shop );
            mShoppingListControl.onCreateShoppingList( shop );
        }
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {
        Log.d(TAG, "onFling: " + e1.toString() + e2.toString());
        final int position = mLV.pointToPosition( Math.round(e1.getX()), Math.round(e1.getY()));
        final String shop = (String) mLV.getItemAtPosition( position );
        boolean result = false;
        DatabaseAccess dbAccess = mShoppingListControl.getDatabaseAccess();
        if ( null == dbAccess ) {
            return false;
        }

        if ( dbAccess.hasShoppingList( shop ) ) {
            dbAccess.deleteShoppingList( shop );
            result = true;
        }

        return super.onFling( e1, e2, velocityX, velocityY );
    }

    @Override
    public boolean onTouch( View v, MotionEvent event ) {
        return mGestureDetector.onTouchEvent(event);
    }
}
