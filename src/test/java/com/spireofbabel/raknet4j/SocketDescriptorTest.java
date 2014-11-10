package com.spireofbabel.raknet4j;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by bcarson on 8/11/14.
 */
public class SocketDescriptorTest {
    @Test
    public void TestConstructor() {
        SocketDescriptor instance = new SocketDescriptor();
        assertThat(instance, is(notNullValue()));
    }

    @Test
    public void TestPortProperty() {
        SocketDescriptor instance = new SocketDescriptor();

        instance.setPort((char)3267);

        assertThat(instance.getPort(), is(equalTo((char) 3267)));
    }

    @Test
    public void TestHostAddressProperty() {
        SocketDescriptor instance = new SocketDescriptor();

        instance.setHostAddress("localhost");

        assertThat(instance.getHostAddress(), is(equalTo("localhost")));
    }
}
