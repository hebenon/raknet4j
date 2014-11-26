package com.spireofbabel.raknet4j;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by bcarson on 6/11/14.
 */
public class ConsoleServerTest {
    @Test
    public void TestGetInstance() {
        ConsoleServer instance = ConsoleServer.GetInstance();

        assertThat(instance, is(notNullValue()));

        ConsoleServer.DestroyInstance(instance);
    }
}
