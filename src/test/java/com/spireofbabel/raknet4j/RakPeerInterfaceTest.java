package com.spireofbabel.raknet4j;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

/**
 * Created by bcarson on 6/11/14.
 */
public class RakPeerInterfaceTest {
    @Test
    public void TestGetInstance() {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        assertThat(instance, is(notNullValue()));

        RakPeerInterface.DestroyInstance(instance);
    }

    @Test
    public void TestStartup() {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)8196, "");

        assertThat(instance.Startup(1, descriptors, -99999), is(RakNetEnums.StartupResult.RAKNET_STARTED));

        instance.Shutdown(0, 0, RakNetEnums.PacketPriority.LOW_PRIORITY);
        RakPeerInterface.DestroyInstance(instance);
    }

    @Test
    public void TestShutdown() {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)8196, "");

        assertThat(instance.Startup(1, descriptors, -99999), is(RakNetEnums.StartupResult.RAKNET_STARTED));

        assertThat(instance.IsActive(), is(equalTo(true)));

        instance.Shutdown(0, 0, RakNetEnums.PacketPriority.LOW_PRIORITY);

        assertThat(instance.IsActive(), is(equalTo(false)));

        RakPeerInterface.DestroyInstance(instance);
    }
}
