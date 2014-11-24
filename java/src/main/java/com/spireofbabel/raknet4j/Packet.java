package com.spireofbabel.raknet4j;

import com.spireofbabel.raknet4j.RakNetSocket2.SystemAddress;
import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 23/11/14.
 */
public class Packet {
    static {
        NativeLibraryUtil.loadNativeLibrary(Packet.class, "RakNetNatives");
    }

    private NativeHandle nativeHandle;

    public Packet() {
        nativeHandle = nativePacket();
    }
    private native NativeHandle nativePacket();

    public Packet(NativeHandle handle) {
        nativeHandle = handle;
    }

    public native SystemAddress getSystemAddress();
    public native void setSystemAddress(SystemAddress address);

    public native RakNetGUID getGuid();
    public native void setGuid(RakNetGUID guid);

    public native long getLength();
    public native void setLength(long length);

    public native long getBitSize();
    public native void setBitSize(long bitSize);

    public native byte [] getData();
    public native void setData(byte [] data);
}
