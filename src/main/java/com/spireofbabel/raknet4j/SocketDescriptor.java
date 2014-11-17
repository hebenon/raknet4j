package com.spireofbabel.raknet4j;

/**
 * Created by bcarson on 8/11/14.
 */

public class SocketDescriptor {
    static {
        String libPath = SocketDescriptor.class.getClassLoader().getResource("dylib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
    }

    private NativeHandle nativeHandle;

    public SocketDescriptor() {
        nativeHandle = nativeSocketDescriptor();
    }

    public SocketDescriptor(int _port, String _hostAddress) {
        nativeHandle = nativeSocketDescriptor();

        setHostAddress(_hostAddress);
        setPort(_port);
    }

    private SocketDescriptor(NativeHandle _nativeHandle) { this.nativeHandle = _nativeHandle; }

    public enum SocketFamily {
        AF_INET,
        AF_INET6,
        AF_UNSPEC
    }

    // Native functions
    private static native NativeHandle nativeSocketDescriptor();
    private static native void nativeDestroyInstance(NativeHandle i);

    native int getPort();
    native void setPort(int port);

    native String getHostAddress();
    native void setHostAddress(String hostAddress);

    native SocketFamily getSocketFamily();
    native void setSocketFamily(SocketFamily socketFamily);

    native boolean getBlockingSocket();
    native void setBlockingSocket(boolean blockingSocket);

    native int getExtraSocketOptions();
    native void setExtraSocketOptions(int extraSocketOptions);
}
