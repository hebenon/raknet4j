package com.spireofbabel.raknet4j;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.spireofbabel.raknet4j.RakNetEnums.ConnectionAttemptResult;
import com.spireofbabel.raknet4j.RakNetEnums.PacketPriority;
import com.spireofbabel.raknet4j.RakNetEnums.PacketReliability;
import com.spireofbabel.raknet4j.RakNetEnums.StartupResult;
import com.spireofbabel.raknet4j.RakNetSocket2.SystemAddress;
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
    public void TestStartupAndShutdown() {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)8196, "");

        assertThat(instance.Startup(1, descriptors, -99999), is(StartupResult.RAKNET_STARTED));

        assertThat(instance.IsActive(), is(equalTo(true)));

        instance.Shutdown(0, 0, PacketPriority.LOW_PRIORITY);

        assertThat(instance.IsActive(), is(equalTo(false)));

        RakPeerInterface.DestroyInstance(instance);
    }

    @Test
    public void TestGetSetMaximumConnections() {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)8196, "");

        assertThat(instance.Startup(1, descriptors, -99999), is(StartupResult.RAKNET_STARTED));

        instance.SetMaximumIncomingConnections(5);

        assertThat(instance.GetMaximumIncomingConnections(), is(equalTo(5)));

        RakPeerInterface.DestroyInstance(instance);
    }

    @Test
    public void TestConnect() {
        RakPeerInterface serverPeer = RakPeerInterface.GetInstance();
        RakPeerInterface clientPeer = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)8196, "127.0.0.1");

        assertThat(serverPeer.Startup(1, descriptors, -99999), is(StartupResult.RAKNET_STARTED));

        SocketDescriptor [] clientDescriptors = new SocketDescriptor[1];
        clientDescriptors[0] = new SocketDescriptor(0, "");

        assertThat(clientPeer.Startup(1, clientDescriptors, -99999), is(StartupResult.RAKNET_STARTED));
        assertThat(clientPeer.Connect("127.0.0.1", 8196, new byte[0], null, 0, 1, 500, 500), is(ConnectionAttemptResult.CONNECTION_ATTEMPT_STARTED));

        RakPeerInterface.DestroyInstance(serverPeer);
        RakPeerInterface.DestroyInstance(clientPeer);
    }

    @Test
    public void TestReceiveShouldReturnNullIfNoPacketPending() {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        assertThat(instance.Receive(), is(nullValue()));

        RakPeerInterface.DestroyInstance(instance);
    }

    @Test
    public void TestSendAndReceive() throws Throwable {
        RakPeerInterface serverPeer = RakPeerInterface.GetInstance();
        RakPeerInterface clientPeer = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)8196, "127.0.0.1");

        assertThat(serverPeer.Startup(1, descriptors, -99999), is(StartupResult.RAKNET_STARTED));

        SocketDescriptor [] clientDescriptors = new SocketDescriptor[1];
        clientDescriptors[0] = new SocketDescriptor(0, "");

        assertThat(clientPeer.Startup(1, clientDescriptors, -99999), is(StartupResult.RAKNET_STARTED));
        assertThat(clientPeer.Connect("127.0.0.1", 8196, new byte[0], null, 0, 1, 500, 500), is(ConnectionAttemptResult.CONNECTION_ATTEMPT_STARTED));

        clientPeer.Send("test".getBytes(),
                PacketPriority.HIGH_PRIORITY,
                PacketReliability.RELIABLE,
                0,
                new AddressOrGUID(new SystemAddress("127.0.0.1",8196)),
                false);

        Thread.currentThread().sleep(500);

        Packet result = serverPeer.Receive();

        assertThat(result, is(notNullValue()));

        RakPeerInterface.DestroyInstance(serverPeer);
        RakPeerInterface.DestroyInstance(clientPeer);
    }

    @Test
    public void TestSendLocalAndReceive()
    {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)18196, "");

        assertThat(instance.Startup(1, descriptors, -99999), is(StartupResult.RAKNET_STARTED));

        instance.SendLoopback("test".getBytes());
        Packet result = instance.Receive();

        assertThat(result, is(notNullValue()));

        RakPeerInterface.DestroyInstance(instance);
    }
}
