package com.spireofbabel.raknet4j.ReplicaManager3;

import com.spireofbabel.raknet4j.LibraryUtility;
import com.spireofbabel.raknet4j.NativeHandle;
import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 13/11/14.
 */
public class SerializeParameters {
    static {
        LibraryUtility.loadNativeLibrary("RakNetNatives");
    }

    private NativeHandle nativeHandle;
    public SerializeParameters() {
        nativeHandle = nativeSerializeParameters();
    }
    public SerializeParameters(NativeHandle _handle) { nativeHandle = _handle; }
    private native NativeHandle nativeSerializeParameters();
}
