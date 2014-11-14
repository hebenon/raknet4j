package com.spireofbabel.raknet4j;

/**
 * Created by bcarson on 13/11/14.
 */
public class RakNetGUID {
    static {
        String libPath = RakNetGUID.class.getClassLoader().getResource("dylib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
    }

    private long nativeHandle;

    public RakNetGUID() {
        nativeHandle = nativeRakNetGUID();
    }
    public RakNetGUID(long _g) {
        nativeHandle = nativeRakNetGUID(_g);
    }

    private native long nativeRakNetGUID();
    private native long nativeRakNetGUID(long _g);
}
