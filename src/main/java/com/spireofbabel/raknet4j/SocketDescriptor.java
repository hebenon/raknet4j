package com.spireofbabel.raknet4j;

/**
 * Created by bcarson on 8/11/14.
 */
/*
struct RAK_DLL_EXPORT SocketDescriptor
{
	SocketDescriptor();
	SocketDescriptor(unsigned short _port, const char *_hostAddress);

	/// The local port to bind to.  Pass 0 to have the OS autoassign a port.
	unsigned short port;

	/// The local network card address to bind to, such as "127.0.0.1".  Pass an empty string to use INADDR_ANY.
	char hostAddress[32];

	/// IP version: For IPV4, use AF_INET (default). For IPV6, use AF_INET6. To autoselect, use AF_UNSPEC.
	/// IPV6 is the newer internet protocol. Instead of addresses such as natpunch.jenkinssoftware.com, you may have an address such as fe80::7c:31f7:fec4:27de%14.
	/// Encoding takes 16 bytes instead of 4, so IPV6 is less efficient for bandwidth.
	/// On the positive side, NAT Punchthrough is not needed and should not be used with IPV6 because there are enough addresses that routers do not need to create address mappings.
	/// RakPeer::Startup() will fail if this IP version is not supported.
	/// \pre RAKNET_SUPPORT_IPV6 must be set to 1 in RakNetDefines.h for AF_INET6
	short socketFamily;









	unsigned short remotePortRakNetWasStartedOn_PS3_PSP2;

	// Required for Google chrome
	_PP_Instance_ chromeInstance;

	// Set to true to use a blocking socket (default, do not change unless you have a reason to)
	bool blockingSocket;

	/// XBOX only: set IPPROTO_VDP if you want to use VDP. If enabled, this socket does not support broadcast to 255.255.255.255
	unsigned int extraSocketOptions;
};
*/
public class SocketDescriptor {
    static {
        String libPath = SocketDescriptor.class.getClassLoader().getResource("dylib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
    }

    private long nativeHandle;

    public SocketDescriptor() {
        nativeHandle = nativeGetInstance();
    }

    public SocketDescriptor(int _port, String _hostAddress) {
        nativeHandle = nativeGetInstance();

        setHostAddress(_hostAddress);
        setPort(_port);
    }

    public enum SocketFamily {
        AF_INET,
        AF_INET6,
        AF_UNSPEC
    }

    // Native functions
    private static native long nativeGetInstance();
    private static native void nativeDestroyInstance(long i);

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
