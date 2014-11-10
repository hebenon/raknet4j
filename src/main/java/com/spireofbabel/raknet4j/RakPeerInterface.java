package com.spireofbabel.raknet4j;

import com.spireofbabel.raknet4j.RakNetEnums.PacketPriority;
import com.spireofbabel.raknet4j.RakNetEnums.StartupResult;

/**
 * Created by bcarson on 8/11/14.
 */

public class RakPeerInterface {
    static {
        String libPath = RakPeerInterface.class.getClassLoader().getResource("dylib/libRakNetNatives.dylib").getPath();
        System.load(libPath);
    }

    private long nativeHandle;

    public RakPeerInterface(long handle) {
        nativeHandle = handle;
    }

    public static RakPeerInterface GetInstance() {
        long handle = nativeGetInstance();
        return new RakPeerInterface(handle);
    }

    public static void DestroyInstance(RakPeerInterface i) {
        nativeDestroyInstance(i.nativeHandle);
    }

    // Native functions
    private static native long nativeGetInstance();
    private static native void nativeDestroyInstance(long i);

	// --------------------------------------------------------------------------------------------Major Low Level Functions - Functions needed by most users--------------------------------------------------------------------------------------------
	/// \brief Starts the network threads and opens the listen port.
	/// \details You must call this before calling Connect().
	/// \pre On the PS3, call Startup() after Client_Login()
	/// \note Multiple calls while already active are ignored.  To call this function again with different settings, you must first call Shutdown().
	/// \note Call SetMaximumIncomingConnections if you want to accept incoming connections.
	/// \param[in] maxConnections Maximum number of connections between this instance of RakPeer and another instance of RakPeer. Required so that the network can preallocate and for thread safety. A pure client would set this to 1.  A pure server would set it to the number of allowed clients.A hybrid would set it to the sum of both types of connections.
	/// \param[in] localPort The port to listen for connections on. On linux the system may be set up so thast ports under 1024 are restricted for everything but the root user. Use a higher port for maximum compatibility.
	/// \param[in] socketDescriptors An array of SocketDescriptor structures to force RakNet to listen on a particular IP address or port (or both).  Each SocketDescriptor will represent one unique socket.  Do not pass redundant structures.  To listen on a specific port, you can pass SocketDescriptor(myPort,0); such as for a server.  For a client, it is usually OK to just pass SocketDescriptor(); However, on the XBOX be sure to use IPPROTO_VDP
	/// \param[in] socketDescriptorCount The size of the \a socketDescriptors array.  Pass 1 if you are not sure what to pass.
	/// \param[in] threadPriority Passed to the thread creation routine. Use THREAD_PRIORITY_NORMAL for Windows. For Linux based systems, you MUST pass something reasonable based on the thread priorities for your application.
	/// \return RAKNET_STARTED on success, otherwise appropriate failure enumeration.
    public native StartupResult Startup(int maxConnections, SocketDescriptor [] socketDescriptors, int threadPriority );

	/// \brief Stops the network threads and closes all connections.
	/// \param[in] blockDuration Wait time(milli seconds) for all remaining messages to go out, including ID_DISCONNECTION_NOTIFICATION.  If 0, it doesn't wait at all.
	/// \param[in] orderingChannel Channel on which ID_DISCONNECTION_NOTIFICATION will be sent, if blockDuration > 0.
	/// \param[in] disconnectionNotificationPriority Priority of sending ID_DISCONNECTION_NOTIFICATION.
	/// If set to 0, the disconnection notification won't be sent.
    public native void Shutdown( int blockDuration, int orderingChannel, PacketPriority disconnectionNotificationPriority );

	/// Returns if the network thread is running
	/// \return true if the network thread is running, false otherwise
	public native boolean IsActive();
}
