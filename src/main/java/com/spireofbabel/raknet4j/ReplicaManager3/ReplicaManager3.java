package com.spireofbabel.raknet4j.ReplicaManager3;

/**
 * Created by bcarson on 11/11/14.
 */
public class ReplicaManager3 {
    static {
        String libPath = ReplicaManager3.class.getClassLoader().getResource("dylib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
    }

    private long nativeHandle;
    public ReplicaManager3() {
        nativeHandle = nativeReplicaManager3();
    }
    private native long nativeReplicaManager3();
}
