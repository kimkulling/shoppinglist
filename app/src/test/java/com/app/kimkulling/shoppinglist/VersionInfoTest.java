package com.app.kimkulling.shoppinglist;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kimku_000 on 22.06.2016.
 */
public class VersionInfoTest {
    @Test
    public void getVersionStringTest() throws Exception {
        final String v = VersionInfo.version();
        assertEquals( "1.0.1", v );
    }
}

