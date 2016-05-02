package com.app.kimkulling.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShoppingListActivity extends AppCompatActivity {
    private static final String TAG         = "ShoppingListActivity";
    private static final String EditFiledId = "shopping_list_editText";

    DatabaseAccess mDBAccess;
    Button mSaveButton;
    Button mCancelButton;
    EditText mShoppingListShop;
    EditText mShoppingListEdit;
    String mItemCache;
    String mShopCache;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mSaveButton = (Button) findViewById( R.id.saveListBtn );
        if ( null == mSaveButton ) {
            Log.d( TAG, "Cannot find save button" );
        } else {
            mSaveButton.setOnClickListener((new View.OnClickListener() {
                public void onClick(View v) {
                    onSaveButton(v);
                }
            }));
        }

        mCancelButton = (Button) findViewById( R.id.cancelListBtn );
        if ( null == mCancelButton ) {
            Log.d( TAG, "Cannot find cancel button" );
        } else {
            mCancelButton.setOnClickListener((new View.OnClickListener() {
                public void onClick(View v) {
                    onCancelButton(v);
                }
            }));
        }
        mShoppingListEdit = ( EditText )findViewById( R.id.shopping_list_editText );
        if ( null == mShoppingListEdit ) {
            Log.d(TAG, "Cannot find ShoppingListEdit field.");
        }

        mShoppingListShop = ( EditText ) findViewById( R.id.shopping_list_shop );
        if ( null == mShoppingListShop ) {
            Log.d(TAG, "Cannot find ShoppingListShop field.");
        }

        mDBAccess = new DatabaseAccess( this, true );
        mDBAccess.open( DatabaseAccess.DatabaseType.ShoppingListType );

        final String shopName = getIntent().getExtras().getString( "shop" );
        clearCache();
        try {
            if (!shopName.equals("none")) {
                ShoppingItem item = mDBAccess.getShoppingListByShop(shopName);
                if (null != item) {
                    mItemCache = item.getItems();
                    mShopCache = item.getShop();
                    mShoppingListShop.setText(mShopCache, TextView.BufferType.EDITABLE);
                    mShoppingListEdit.setText(mItemCache, TextView.BufferType.EDITABLE);
                }
            }
        } catch( NullPointerException e ) {
            Log.e( TAG, "Null pointer exception:" );
            Log.e( TAG, e.getStackTrace().toString() );
        }

        Log.d( TAG, "Shop-Cache = " + mShopCache + ", ItemsCache = " + mItemCache );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBAccess.close();
    }

    private void backToMainActivity() {
        Intent intent = new Intent( this, MainActivity.class );
        startActivity(intent);
    }

    private void storeShoppingList() {
        if ( mShoppingListShop == null ) {
            Log.d( TAG, "EditText attribute mShoppingListShop is null." );
            return;
        }

        String shopName = mShoppingListShop.getText().toString();
        String items = mShoppingListEdit.getText().toString();
        items = ShoppingItem.processItems( items );
        if ( !shopName.equals( mShopCache ) ) {
            // create a new shopping list entry
            Log.d(TAG, "storeShoppingList: new " + shopName + " != " + mShopCache );
            mDBAccess.addNewShoppingList(shopName, items);
        } else {
            // Modify an existing shopping list
            Log.d(TAG, "storeShoppingList: modify" );
            mDBAccess.modifyShoppingLists( shopName, items );
        }
    }

    private void onSaveButton( final View sView ) {
        Log.d(TAG, "onSaveButton");
        storeShoppingList();
        backToMainActivity();
        Log.d(TAG, "new Intent to create MainActivity");
    }

    private void onCancelButton( final View sView ) {
        Log.d(TAG, "onCancelButton");
        backToMainActivity();
        Log.d( TAG, "new Intent to create MainActivity" );
    }

    private void clearCache() {
        mItemCache = "";
        mShopCache = "";
    }
}
