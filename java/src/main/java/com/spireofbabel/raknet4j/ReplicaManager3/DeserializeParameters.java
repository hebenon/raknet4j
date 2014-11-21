package com.spireofbabel.raknet4j.ReplicaManager3;

import com.spireofbabel.raknet4j.NativeHandle;

/**
 * Created by bcarson on 13/11/14.
 */
public class DeserializeParameters {
    static {
        String libPath = DeserializeParameters.class.getClassLoader().getResource("lib/libRakNetNatives.jnilib").getPath();
        System.load(libPath);
    }

    private NativeHandle nativeHandle;
    public DeserializeParameters() {
        nativeHandle = nativeDeserializeParameters();
    }
    private native NativeHandle nativeDeserializeParameters();
}
