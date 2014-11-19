package com.spireofbabel.raknet4j.ReplicaManager3;

import com.spireofbabel.raknet4j.NativeHandle;
import com.spireofbabel.raknet4j.PluginInterface2;
import com.spireofbabel.raknet4j.RakNetGUID;
import com.spireofbabel.raknet4j.RakNetSocket2.SystemAddress;

/**
 * Created by bcarson on 11/11/14.
 */
public abstract class ReplicaManager3 extends PluginInterface2 {
    static {
        String libPath = ReplicaManager3.class.getClassLoader().getResource("dylib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
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
