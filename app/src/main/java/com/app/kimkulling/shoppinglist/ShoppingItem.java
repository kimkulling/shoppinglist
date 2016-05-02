package com.app.kimkulling.shoppinglist;

/**
 * Created by kimku_000 on 16.02.2016.
 */
public class ShoppingItem {
    String mShop;
    String mItems;

    public ShoppingItem( final String shop, final String items ) {
        mShop = shop;
        mItems = items;
    }

    void setShop( final String newShop ) {
        this.mShop = newShop;
    }

    String getShop() {
        return mShop;
    }

    void setItems( final String items ) {
        mItems = items;
    }

    String getItems() {
        return mItems;
    }

    static public String processItems( String items ) {
        if ( 0 == items.length() ) {
            return items;
        }

        String sevItems[] = items.split( "," ), proceededItems[] = null;
        if ( null == sevItems ) {
            String spaceItems[] = items.split( " " );
            if ( null == spaceItems ) {
                return items;
            } else {
                proceededItems = spaceItems;
            }
        } else {
            proceededItems = sevItems;
        }

        String newItems = new String();
        for ( int i=0; i<proceededItems.length; i++ ) {
            String currentItem = proceededItems[ i ];
            currentItem = currentItem.trim() + "\n";
            newItems = newItems + currentItem;
        }

        return newItems;
    }
}
