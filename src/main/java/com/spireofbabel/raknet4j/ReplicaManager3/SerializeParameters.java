package com.spireofbabel.raknet4j.ReplicaManager3;

/**
 * Created by bcarson on 13/11/14.
 */
public class SerializeParameters {
    static {
        String libPath = SerializeParameters.class.getClassLoader().getResource("dylib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
    }

    private long nativeHandle;
    public SerializeParameters() {
        nativeHandle = nativeSerializeParameters();
    }
    private native long nativeSerializeParameters();
}
