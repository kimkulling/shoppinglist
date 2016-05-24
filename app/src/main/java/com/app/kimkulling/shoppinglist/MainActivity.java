package com.app.kimkulling.shoppinglist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mNewListButton;
    private DatabaseAccess mDBAccess;
    private ListView mSLView;
    private ArrayAdapter<String> mSLAdapter;
    private GestureDetectorCompat mDetector;
    private ShoppingListControl mShoppingListControl;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDBAccess = new DatabaseAccess( this, true );
        mShoppingListControl = new ShoppingListControl( this, mDBAccess );

        mNewListButton = (Button) findViewById( R.id.new_list_button );
        mNewListButton.setOnClickListener( ( new View.OnClickListener() {
            public void onClick(View v) {
                mShoppingListControl.onCreateShoppingList( "new" );
            }
        } ) );

        mSLView = (ListView) findViewById( R.id.shoppingListView );
        if ( null == mSLView ) {
            Log.d( TAG, "Cannot find List view." );
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        if ( mDBAccess.exists( DatabaseAccess.DatabaseType.ShoppingListType)) {
            mDBAccess.open( DatabaseAccess.DatabaseType.ShoppingListType);
            ShoppingItem[] items = mDBAccess.readAllShoppingLists();
            if ( null != items ) {
                final ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < items.length; i++) {
                    list.add(items[i].getShop());
                }
                mSLAdapter = new ArrayAdapter<String>(this, R.layout.shoppinglist_text_view, list);
                mSLView.setAdapter(mSLAdapter);
            }
        } else {
            Log.d( TAG, "No database for Shopping list." );
        }
        GestureListener gestureListener = new GestureListener( this, mSLView, mShoppingListControl );
        mDetector = new GestureDetectorCompat( this, gestureListener );
        gestureListener.setGestureDetector( mDetector );
        mSLView.setOnTouchListener( gestureListener );
    }

    public ArrayAdapter<String> getLVAdapter() {
        return mSLAdapter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBAccess.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if ( id == R.id.action_main_about ) {
            AboutDialog.show( this );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.app.kimkulling.shoppinglist/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.app.kimkulling.shoppinglist/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
