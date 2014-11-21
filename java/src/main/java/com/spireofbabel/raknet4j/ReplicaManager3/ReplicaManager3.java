package com.spireofbabel.raknet4j.ReplicaManager3;

import com.spireofbabel.raknet4j.NativeHandle;
import com.spireofbabel.raknet4j.PluginInterface2;
import com.spireofbabel.raknet4j.RakNetGUID;
import com.spireofbabel.raknet4j.RakNetSocket2.SystemAddress;
import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 11/11/14.
 */
public abstract class ReplicaManager3 extends PluginInterface2 {
    static {
        NativeLibraryUtil.loadNativeLibrary(ReplicaManager3.class, "RakNetNatives");
    }

    private NativeHandle nativeHandle;
    public ReplicaManager3() {
        nativeHandle = nativeReplicaManager3();
    }
    public ReplicaManager3(NativeHandle _handle) { nativeHandle = _handle; }
    private native NativeHandle nativeReplicaManager3();

    public abstract Connection_RM3 AllocConnection(SystemAddress systemAddress, RakNetGUID rakNetGUID);
    public abstract void DeallocConnection(Connection_RM3 connection);
}
