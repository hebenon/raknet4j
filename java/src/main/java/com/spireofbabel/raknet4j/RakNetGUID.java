package com.spireofbabel.raknet4j;

import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 13/11/14.
 */
public class RakNetGUID {
    static {
        LibraryUtility.loadNativeLibrary("RakNetNatives");
    }

    private NativeHandle nativeHandle;

    public RakNetGUID() {
        nativeHandle = nativeRakNetGUID();
    }

    private RakNetGUID(NativeHandle _handle) {
        nativeHandle = _handle;
    }

    public RakNetGUID(long _g) {
        nativeHandle = nativeRakNetGUID(_g);
    }

    private native NativeHandle nativeRakNetGUID();
    private native NativeHandle nativeRakNetGUID(long _g);
}
