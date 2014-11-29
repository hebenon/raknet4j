package com.spireofbabel.raknet4j;

import com.spireofbabel.raknet4j.ReplicaManager3.ReplicaManager3Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConsoleServerTest.class,
        RakPeerInterfaceTest.class,
        SocketDescriptorTest.class,
        ReplicaManager3Test.class
})
public class AllTests {
}
