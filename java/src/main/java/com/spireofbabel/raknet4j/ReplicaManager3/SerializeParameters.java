package com.spireofbabel.raknet4j.ReplicaManager3;

import com.spireofbabel.raknet4j.NativeHandle;

/**
 * Created by bcarson on 13/11/14.
 */
public class SerializeParameters {
    static {
        String libPath = SerializeParameters.class.getClassLoader().getResource("lib/libRakNetNatives.jnilib").getPath();
        System.load(libPath);
    }

    private NativeHandle nativeHandle;
    public SerializeParameters() {
        nativeHandle = nativeSerializeParameters();
    }
    public SerializeParameters(NativeHandle _handle) { nativeHandle = _handle; }
    private native NativeHandle nativeSerializeParameters();
}
