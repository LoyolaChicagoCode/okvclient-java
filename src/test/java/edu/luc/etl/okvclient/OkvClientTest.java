package edu.luc.etl.okvclient;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by laufer on 10/22/13.
 */
public class OkvClientTest {
    @Test public void testSingleton() {
        org.junit.Assert.assertNotNull(OkvClient.INSTANCE);
    }
}
