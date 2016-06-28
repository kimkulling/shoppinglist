package com.app.kimkulling.shoppinglist;

import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by kimku_000 on 09.04.2016.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener implements
        View.OnTouchListener {
    private static final String TAG = "GestureListener";

    private final ListView mLV;
    private GestureDetectorCompat mGestureDetector;
    private ShoppingListControl mShoppingListControl;
    private final MainActivity mParentActivity;
    private boolean mIsDown;
    private boolean mIsOnFling;
    private String mSelectedShop;

    public GestureListener( MainActivity parent, ListView lv, ShoppingListControl control ) {
        super();

        mLV = lv;
        mParentActivity = parent;
        mGestureDetector = null;
        mShoppingListControl = control;
        mIsDown = false;
        mIsOnFling = false;
        mSelectedShop = "";
    }

    public void setGestureDetector( GestureDetectorCompat gd ) {
        mGestureDetector = gd;
    }

    @Override
    public boolean onDown( MotionEvent event ) {
        Log.d(TAG,"onDown: " + event.toString());
        final int position = mLV.pointToPosition( Math.round(event.getX()), Math.round(event.getY()));
        final String shop = (String) mLV.getItemAtPosition( position );
        if ( mShoppingListControl.getDatabaseAccess().hasShoppingList( shop ) ) {
            Log.d( TAG, "clicked on" + shop );
            mIsDown = true;
            mSelectedShop = shop;
        }
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG,"onSingleTapUp: " + e.toString());
        if ( mIsDown && !mIsOnFling ) {
            mShoppingListControl.onCreateShoppingList( mSelectedShop );
        }

        resetGestureStates();

        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: " + e1.toString() + e2.toString());
        final int position = mLV.pointToPosition( Math.round(e1.getX()), Math.round(e1.getY()));
        final String shop = (String) mLV.getItemAtPosition( position );
        mIsOnFling = true;
        DatabaseAccess dbAccess = mShoppingListControl.getDatabaseAccess();
        if ( null != dbAccess ) {
            if ( dbAccess.hasShoppingList( mSelectedShop ) ) {
                dbAccess.deleteShoppingList(mSelectedShop);
                mParentActivity.getLVAdapter().clear();
                ShoppingItem[] items = dbAccess.readAllShoppingLists();
                if (null != items) {
                    final ArrayList<String> list = new ArrayList<String>();
                    for (int i = 0; i < items.length; i++) {
                        mParentActivity.getLVAdapter().add( items[ i ].getShop() );
                    }
                }
            }
        }
        resetGestureStates();
        mParentActivity.showMessage( R.string.msg_deleting_shop );

        return super.onFling( e1, e2, velocityX, velocityY );
    }

    @Override
    public boolean onTouch( View v, MotionEvent event ) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void resetGestureStates() {
        mIsDown = false;
        mIsOnFling = false;
        mSelectedShop = "";
    }
}
