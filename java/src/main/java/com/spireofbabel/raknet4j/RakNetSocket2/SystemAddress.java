package com.spireofbabel.raknet4j.RakNetSocket2;

import com.spireofbabel.raknet4j.NativeHandle;

/**
 * Created by bcarson on 11/11/14.
 */
public class SystemAddress {
    private NativeHandle nativeHandle;

    public SystemAddress() { nativeHandle = nativeSystemAddress(); }
   	public SystemAddress(String str) { nativeHandle = nativeSystemAddress(str); }
   	public SystemAddress(String str, int port) { nativeHandle = nativeSystemAddress(str, port); }

    // Native functions
    private native NativeHandle nativeSystemAddress();
    private native NativeHandle nativeSystemAddress(String str);
    private native NativeHandle nativeSystemAddress(String str, int port);
    public static native void nativeDestroyInstance(NativeHandle i);
}
