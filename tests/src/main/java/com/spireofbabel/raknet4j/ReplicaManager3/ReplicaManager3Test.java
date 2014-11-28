package com.spireofbabel.raknet4j.ReplicaManager3;

import junit.framework.TestCase;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.spireofbabel.raknet4j.RakNetEnums.StartupResult;
import com.spireofbabel.raknet4j.RakNetGUID;
import com.spireofbabel.raknet4j.RakNetSocket2.SystemAddress;
import com.spireofbabel.raknet4j.RakPeerInterface;
import com.spireofbabel.raknet4j.SocketDescriptor;
import org.junit.Test;

/**
 * Created by bcarson on 16/11/14.
 */
public class ReplicaManager3Test extends TestCase {
    @Test
    public void testAttachToRakPeerInterface() {
        RakPeerInterface instance = RakPeerInterface.GetInstance();

        SocketDescriptor[] descriptors = new SocketDescriptor[1];
        descriptors[0] = new SocketDescriptor((short)8196, "");

        assertThat(instance.Startup(1, descriptors, -99999), is(StartupResult.RAKNET_STARTED));

        // Create an instance and add it to the peer.
        ReplicaManager3 replicaManager3 = new ReplicaManager3() {
            @Override
            public Connection_RM3 AllocConnection(SystemAddress systemAddress, RakNetGUID rakNetGUID) {
                return null;
            }

            @Override
            public void DeallocConnection(Connection_RM3 connection) {

            }
        };

        instance.AttachPlugin(replicaManager3);

        RakPeerInterface.DestroyInstance(instance);
    }
}
