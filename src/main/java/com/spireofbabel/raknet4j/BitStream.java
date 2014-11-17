package com.spireofbabel.raknet4j;

/**
 * Created by bcarson on 13/11/14.
 */
public class BitStream {
    static {
        String libPath = BitStream.class.getClassLoader().getResource("dylib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
    }

    private NativeHandle nativeHandle;

    public BitStream(NativeHandle handle) { nativeHandle = handle; }

    public static BitStream GetInstance() { return new BitStream(nativeGetInstance()); }
    public static void DestroyInstance(BitStream i) { nativeDestroyInstance(i.nativeHandle); }

    private static native NativeHandle nativeGetInstance();
    private static native NativeHandle nativeDestroyInstance(NativeHandle i);
}
