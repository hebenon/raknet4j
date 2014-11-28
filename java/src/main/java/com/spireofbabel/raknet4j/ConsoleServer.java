package com.spireofbabel.raknet4j;

import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 6/11/14.
 */

public class ConsoleServer  {
    static {
        LibraryUtility.loadNativeLibrary("RakNetNatives");
    }

    private NativeHandle nativeHandle;

    public ConsoleServer(NativeHandle handle) {
        nativeHandle = handle;
    }

    public static ConsoleServer GetInstance() {
        return new ConsoleServer(nativeGetInstance());
    }

    public static void DestroyInstance(ConsoleServer i) {
        nativeDestroyInstance(i.nativeHandle);
    }

    // Native functions
    private static native NativeHandle nativeGetInstance();
    private static native void nativeDestroyInstance(NativeHandle i);
}
