package com.spireofbabel.raknet4j;

/**
 * Created by bcarson on 6/11/14.
 */

public class ConsoleServer  {
    static {
        String libPath = ConsoleServer.class.getClassLoader().getResource("dylib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
    }

    private long nativeHandle;

    public ConsoleServer(long handle) {
        nativeHandle = handle;
    }

    public static ConsoleServer GetInstance() {
        return new ConsoleServer(nativeGetInstance());
    }

    public static void DestroyInstance(ConsoleServer i) {
        nativeDestroyInstance(i.nativeHandle);
    }

    // Native functions
    private static native long nativeGetInstance();
    private static native void nativeDestroyInstance(long i);
}
