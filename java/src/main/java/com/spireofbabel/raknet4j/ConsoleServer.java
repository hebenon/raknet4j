package com.spireofbabel.raknet4j;

/**
 * Created by bcarson on 6/11/14.
 */

public class ConsoleServer  {
    static {
        String libPath = ConsoleServer.class.getClassLoader().getResource("lib/libRakNetNatives.jnilib").getPath();
        System.load(libPath);
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
