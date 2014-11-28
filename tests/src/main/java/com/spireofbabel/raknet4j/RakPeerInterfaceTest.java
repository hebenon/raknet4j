package com.spireofbabel.raknet4j;

import junit.framework.TestCase;

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
public class RakPeerInterfaceTest extends TestCase {
    @Test
    public void testGetInstance() {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        assertThat(instance, is(notNullValue()));

        RakPeerInterface.DestroyInstance(instance);
    }

    @Test
    public void testStartupAndShutdown() {
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
    public void testGetSetMaximumConnections() {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)8196, "");

        assertThat(instance.Startup(1, descriptors, -99999), is(StartupResult.RAKNET_STARTED));

        instance.SetMaximumIncomingConnections(5);

        assertThat(instance.GetMaximumIncomingConnections(), is(equalTo(5)));

        RakPeerInterface.DestroyInstance(instance);
    }

    @Test
    public void testConnect() throws Throwable {
        RakPeerInterface serverPeer = RakPeerInterface.GetInstance();
        RakPeerInterface clientPeer = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)28196, "127.0.0.1");

        assertThat(serverPeer.Startup(50, descriptors, -99999), is(StartupResult.RAKNET_STARTED));
        serverPeer.SetMaximumIncomingConnections(50);

        SocketDescriptor [] clientDescriptors = new SocketDescriptor[1];
        clientDescriptors[0] = new SocketDescriptor(0, "");

        assertThat(clientPeer.Startup(1, clientDescriptors, -99999), is(StartupResult.RAKNET_STARTED));
        assertThat(clientPeer.Connect("127.0.0.1", 28196, new byte[0], null, 0, 1, 500, 500), is(ConnectionAttemptResult.CONNECTION_ATTEMPT_STARTED));

        Thread.currentThread().sleep(100);

        Packet result = clientPeer.Receive();
        assertThat((int)result.getData()[0], is(DefaultMessageIDTypes.ID_CONNECTION_REQUEST_ACCEPTED.ordinal()));

        RakPeerInterface.DestroyInstance(serverPeer);
        RakPeerInterface.DestroyInstance(clientPeer);
    }

    @Test
    public void testReceiveShouldReturnNullIfNoPacketPending() {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        assertThat(instance.Receive(), is(nullValue()));

        RakPeerInterface.DestroyInstance(instance);
    }

    @Test
    public void testSendAndReceive() throws Throwable {
        RakPeerInterface serverPeer = RakPeerInterface.GetInstance();
        RakPeerInterface clientPeer = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)8196, "");

        assertThat(serverPeer.Startup(50, descriptors, 1), is(StartupResult.RAKNET_STARTED));
        serverPeer.SetMaximumIncomingConnections(50);

        SocketDescriptor [] clientDescriptors = new SocketDescriptor[1];
        clientDescriptors[0] = new SocketDescriptor(0, "");

        assertThat(clientPeer.Startup(1, clientDescriptors, 1), is(StartupResult.RAKNET_STARTED));
        assertThat(clientPeer.Connect("127.0.0.1", 8196, new byte[0], null, 0, 1, 500, 500), is(ConnectionAttemptResult.CONNECTION_ATTEMPT_STARTED));

        Thread.currentThread().sleep(100);

        // Check a connect response is received (and swallow the packet)
        Packet connectPacket = serverPeer.Receive();
        assertThat(DefaultMessageIDTypes.values()[connectPacket.getData()[0]], is(DefaultMessageIDTypes.ID_NEW_INCOMING_CONNECTION));
        serverPeer.DeallocatePacket(connectPacket);

        clientPeer.Send("test".getBytes(),
                PacketPriority.HIGH_PRIORITY,
                PacketReliability.RELIABLE_ORDERED,
                0,
                new AddressOrGUID(),
                true);

        Thread.currentThread().sleep(100);

        Packet result = serverPeer.Receive();

        assertThat(result, is(notNullValue()));
        assertThat(new String(result.getData(), "UTF-8"), is(equalTo("test")));

        serverPeer.DeallocatePacket(result);

        RakPeerInterface.DestroyInstance(serverPeer);
        RakPeerInterface.DestroyInstance(clientPeer);
    }

    @Test
    public void testSendLocalAndReceive() throws Throwable
    {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)18196, "");

        assertThat(instance.Startup(1, descriptors, -99999), is(StartupResult.RAKNET_STARTED));

        instance.SendLoopback("test".getBytes());
        Packet result = instance.Receive();

        assertThat(result, is(notNullValue()));
        assertThat(new String(result.getData(), "UTF-8"), is(equalTo("test")));

        instance.DeallocatePacket(result);

        RakPeerInterface.DestroyInstance(instance);
    }

    @Test
    public void testSendListAndReceive() throws Throwable {
        RakPeerInterface serverPeer = RakPeerInterface.GetInstance();
        RakPeerInterface clientPeer = RakPeerInterface.GetInstance();

        SocketDescriptor [] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)58196, "");

        assertThat(serverPeer.Startup(50, descriptors, 1), is(StartupResult.RAKNET_STARTED));
        serverPeer.SetMaximumIncomingConnections(50);

        SocketDescriptor [] clientDescriptors = new SocketDescriptor[1];
        clientDescriptors[0] = new SocketDescriptor(0, "");

        assertThat(clientPeer.Startup(1, clientDescriptors, 1), is(StartupResult.RAKNET_STARTED));
        assertThat(clientPeer.Connect("127.0.0.1", 58196, new byte[0], null, 0, 1, 500, 500), is(ConnectionAttemptResult.CONNECTION_ATTEMPT_STARTED));

        Thread.currentThread().sleep(100);

        // Check a connect response is received (and swallow the packet)
        Packet connectPacket = serverPeer.Receive();
        assertThat(DefaultMessageIDTypes.values()[connectPacket.getData()[0]], is(DefaultMessageIDTypes.ID_NEW_INCOMING_CONNECTION));
        serverPeer.DeallocatePacket(connectPacket);

        byte [][] data = new byte[][] { "Combined".getBytes(), " result".getBytes(), " is combined".getBytes() };

        clientPeer.SendList(data,
                PacketPriority.HIGH_PRIORITY,
                PacketReliability.RELIABLE_ORDERED,
                0,
                new AddressOrGUID(),
                true);

        Thread.currentThread().sleep(100);

        Packet result = serverPeer.Receive();

        assertThat(result, is(notNullValue()));
        assertThat(new String(result.getData(), "UTF-8"), is(equalTo("Combined result is combined")));

        serverPeer.DeallocatePacket(result);

        RakPeerInterface.DestroyInstance(serverPeer);
        RakPeerInterface.DestroyInstance(clientPeer);
    }
}
