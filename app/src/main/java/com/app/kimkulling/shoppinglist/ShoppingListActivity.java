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

    private DatabaseAccess mDBAccess;
    private Button mBackButton;
    private Button mSaveButton;
    private EditText mShoppingListShop;
    private EditText mShoppingListEdit;
    private String mItemCache;
    private String mShopCache;
    private MainActivity mParentActivity;

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

        mBackButton = (Button) findViewById( R.id.backListBtn );
        if ( null == mBackButton ) {
            Log.d( TAG, "Cannot find cancel button" );
        } else {
            mBackButton.setOnClickListener((new View.OnClickListener() {
                public void onClick(View v) {
                    onBackButton(v);
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

    private void backToMainActivity( final String message ) {
        Intent intent = new Intent( this, MainActivity.class );
        if ( 0 != message.length()) {
            intent.putExtra( "message", message );
        }
        startActivity( intent );
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
            mDBAccess.addNewShoppingList( shopName, items );
            backToMainActivity( "new" );
        } else {
            //mParentActivity.showMessage( R.string.msg_add_shop );
            // Modify an existing shopping list
            Log.d(TAG, "storeShoppingList: modify" );
            mDBAccess.modifyShoppingLists( shopName, items );
            backToMainActivity( "modify" );
        }
    }

    private void onBackButton( final View sView ) {
        Log.d(TAG, "onBackButton");
        backToMainActivity( "" );
        Log.d( TAG, "new Intent to create MainActivity" );
    }

    private void onSaveButton( final View sView ) {
        Log.d(TAG, "onSaveButton");
        storeShoppingList();
    }

    private void clearCache() {
        mItemCache = "";
        mShopCache = "";
    }
}
