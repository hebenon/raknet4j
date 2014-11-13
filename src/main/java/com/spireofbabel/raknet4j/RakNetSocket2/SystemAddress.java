package com.spireofbabel.raknet4j.RakNetSocket2;

/**
 * Created by bcarson on 11/11/14.
 */
public class SystemAddress {
    private long nativeHandle;

    public SystemAddress() { nativeHandle = nativeGetInstance(); }
   	public SystemAddress(String str) { nativeHandle = nativeGetInstance(); }
   	public SystemAddress(String str, int port) { nativeHandle = nativeGetInstance(); }

    // Native functions
    private static native long nativeGetInstance();
    private static native void nativeDestroyInstance(long i);
}
