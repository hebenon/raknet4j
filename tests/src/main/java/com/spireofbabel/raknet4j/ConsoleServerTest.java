package com.spireofbabel.raknet4j;

import org.junit.Test;
import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by bcarson on 6/11/14.
 */
public class ConsoleServerTest extends TestCase {
    @Test
    public void testGetInstance() {
        ConsoleServer instance = ConsoleServer.GetInstance();

        assertThat(instance, is(notNullValue()));

        ConsoleServer.DestroyInstance(instance);
    }
}
