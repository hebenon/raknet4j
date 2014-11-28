package com.spireofbabel.raknet4j;

import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 13/11/14.
 */
public class BitStream {
    static {
        LibraryUtility.loadNativeLibrary("RakNetNatives");
    }

    private NativeHandle nativeHandle;

    public BitStream(NativeHandle handle) { nativeHandle = handle; }

    public static BitStream GetInstance() { return new BitStream(nativeGetInstance()); }
    public static void DestroyInstance(BitStream i) { nativeDestroyInstance(i.nativeHandle); }

    private static native NativeHandle nativeGetInstance();
    private static native NativeHandle nativeDestroyInstance(NativeHandle i);
}
