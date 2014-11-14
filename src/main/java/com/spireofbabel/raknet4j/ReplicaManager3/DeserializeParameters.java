package com.spireofbabel.raknet4j.ReplicaManager3;

/**
 * Created by bcarson on 13/11/14.
 */
public class DeserializeParameters {
    static {
        String libPath = DeserializeParameters.class.getClassLoader().getResource("dylib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
    }

    private long nativeHandle;
    public DeserializeParameters() {
        nativeHandle = nativeDeserializeParameters();
    }
    private native long nativeDeserializeParameters();
}
