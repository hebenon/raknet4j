package com.spireofbabel.raknet4j;

import com.spireofbabel.raknet4j.ReplicaManager3.ReplicaManager3Test;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {

    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(ConsoleServerTest.class);
        suite.addTestSuite(RakPeerInterfaceTest.class);
        suite.addTestSuite(SocketDescriptorTest.class);
        suite.addTestSuite(ReplicaManager3Test.class);

        return suite;
    }
}
