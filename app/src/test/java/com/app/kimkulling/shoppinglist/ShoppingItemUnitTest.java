package com.app.kimkulling.shoppinglist;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class is used to test the shopping item class.
 */
public class ShoppingItemUnitTest {
    @Test
    public void accessShop() throws Exception {
        ShoppingItem theItem = new ShoppingItem( "edeka", "wurst" );
        assertEquals( theItem.getShop(), "edeka" );
    }

    @Test
    public void accessItems() throws Exception {
        ShoppingItem theItem = new ShoppingItem( "edeka", "wurst" );
        assertEquals( theItem.getItems(), "wurst" );
    }

    @Test
    public void separateStringByComma() throws Exception {
        String items = new String("a, b, c, d" );
        String proceededItems = ShoppingItem.processItems( items );
        assertEquals( "a\nb\nc\nd\n", proceededItems );
    }
}
