package com.spireofbabel.raknet4j;

/**
 * Created by bcarson on 13/11/14.
 */
public class RakNetGUID {
    static {
        String libPath = RakNetGUID.class.getClassLoader().getResource("lib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
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
