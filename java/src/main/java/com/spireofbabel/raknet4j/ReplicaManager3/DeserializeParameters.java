package com.spireofbabel.raknet4j.ReplicaManager3;

import com.spireofbabel.raknet4j.LibraryUtility;
import com.spireofbabel.raknet4j.NativeHandle;
import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 13/11/14.
 */
public class DeserializeParameters {
    static {
        LibraryUtility.loadNativeLibrary("RakNetNatives");
    }

    private NativeHandle nativeHandle;
    public DeserializeParameters() {
        nativeHandle = nativeDeserializeParameters();
    }
    private native NativeHandle nativeDeserializeParameters();
}
