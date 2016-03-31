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
}
