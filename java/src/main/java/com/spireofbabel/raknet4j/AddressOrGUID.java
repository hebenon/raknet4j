package com.spireofbabel.raknet4j;

import com.spireofbabel.raknet4j.RakNetSocket2.SystemAddress;
import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 23/11/14.
 */
public class AddressOrGUID {
    static {
        NativeLibraryUtil.loadNativeLibrary(AddressOrGUID.class, "RakNetNatives");
    }

    private NativeHandle nativeHandle;

    public AddressOrGUID(NativeHandle handle) {
        nativeHandle = handle;
    }

    public AddressOrGUID( AddressOrGUID other) {
        nativeHandle = nativeAddressOrGUID(other);
    }
    private native NativeHandle nativeAddressOrGUID(AddressOrGUID other);

    public AddressOrGUID( SystemAddress address) {
        nativeHandle = nativeAddressOrGUID(address);
    }
    private native NativeHandle nativeAddressOrGUID(SystemAddress address);

    public AddressOrGUID( RakNetGUID guid) {
        nativeHandle = nativeAddressOrGUID(guid);
    }
    private native NativeHandle nativeAddressOrGUID(RakNetGUID guid);
}
