package com.app.kimkulling.shoppinglist;

/**
 *  This class is used to define a bundle of shopping items.
 */
public class ShoppingItem {
    String mShop;
    String mItems;

    /**
     * The class constructor.
     * @param shop  The nae of the shop
     * @param items The list of items
     */
    public ShoppingItem( final String shop, final String items ) {
        mShop = shop;
        mItems = items;
    }

    /**
     * Set a new shop name.
     * @param newShop   The new shop name.
     */
    void setShop( final String newShop ) {
        this.mShop = newShop;
    }

    /**
     *  Returns the shop name.
     * @return  The name of the shop.
     */
    String getShop() {
        return mShop;
    }

    /**
     * Set the item list.
     * @param items The item list as a single string.
     */
    void setItems( final String items ) {
        mItems = items;
    }

    /**
     * Returns the item list.
     * @return  The item list as a string.
     */
    String getItems() {
        return mItems;
    }

    /**
     * Item processor to get the items formatted in a nice way.
     * @param items
     * @return
     */
    static public String processItems( final String items ) {
        if ( 0 == items.length() ) {
            return items;
        }

        String sep[] = { "," }, proceededItems[] = null;
        String regexp[] = { ",", "\\s+" };

        for ( int i=0; i<sep.length; i++) {
            if ( items.contains( sep[ i ] ) ) {
                String sepItems[] = items.split(regexp[ i ] );
                proceededItems = sepItems;
                break;
            }
        }

        String newItems = new String();
        if ( null != proceededItems ) {
            for ( String currentItem: proceededItems ) {
                currentItem = currentItem.trim() + "\n";
                newItems = newItems + currentItem;
            }
        } else {
            newItems = items;
        }

        return newItems;
    }
}
