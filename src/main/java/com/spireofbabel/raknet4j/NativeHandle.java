package com.spireofbabel.raknet4j;

/**
 * Created by bcarson on 16/11/14.
 */
public class NativeHandle {
    private long nativeHandle;

    public NativeHandle(long _nativeHandle) {
        nativeHandle = _nativeHandle;
    }

    public long getNativeHandle() { return nativeHandle; }
}
