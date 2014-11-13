package com.spireofbabel.raknet4j;

import com.spireofbabel.raknet4j.RakNetEnums.PacketPriority;
import com.spireofbabel.raknet4j.RakNetEnums.StartupResult;
import com.spireofbabel.raknet4j.RakNetEnums.ConnectionAttemptResult;
import com.spireofbabel.raknet4j.RakNetSocket2.RakNetSocket2;

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

	/// If you accept connections, you must call this or else security will not be enabled for incoming connections.
	/// This feature requires more round trips, bandwidth, and CPU time for the connection handshake
	/// x64 builds require under 25% of the CPU time of other builds
	/// See the Encryption sample for example usage
	/// \pre Must be called while offline
	/// \pre LIBCAT_SECURITY must be defined to 1 in NativeFeatureIncludes.h for this function to have any effect
	/// \param[in] publicKey A pointer to the public key for accepting new connections
	/// \param[in] privateKey A pointer to the private key for accepting new connections
	/// \param[in] bRequireClientKey: Should be set to false for most servers.  Allows the server to accept a public key from connecting clients as a proof of identity but eats twice as much CPU time as a normal connection
	public native boolean InitializeSecurity( byte [] publicKey, byte [] privateKey, boolean bRequireClientKey );

	/// Disables security for incoming connections.
	/// \note Must be called while offline
	public native void DisableSecurity();

	/// If secure connections are on, do not use secure connections for a specific IP address.
	/// This is useful if you have a fixed-address internal server behind a LAN.
	/// \note Secure connections are determined by the recipient of an incoming connection. This has no effect if called on the system attempting to connect.
	/// \param[in] ip IP address to add. * wildcards are supported.
	public native void AddToSecurityExceptionList(String ip);

	/// Remove a specific connection previously added via AddToSecurityExceptionList
	/// \param[in] ip IP address to remove. Pass 0 to remove all IP addresses. * wildcards are supported.
	public native void RemoveFromSecurityExceptionList(String ip);

	/// Checks to see if a given IP is in the security exception list
	/// \param[in] IP address to check.
	public native boolean IsInSecurityExceptionList(String ip);

	/// Sets how many incoming connections are allowed. If this is less than the number of players currently connected,
	/// no more players will be allowed to connect.  If this is greater than the maximum number of peers allowed,
	/// it will be reduced to the maximum number of peers allowed.
	/// Defaults to 0, meaning by default, nobody can connect to you
	/// \param[in] numberAllowed Maximum number of incoming connections allowed.
	public native void SetMaximumIncomingConnections( int numberAllowed );

	/// Returns the value passed to SetMaximumIncomingConnections()
	/// \return the maximum number of incoming connections, which is always <= maxConnections
	public native int GetMaximumIncomingConnections();

	/// Returns how many open connections there are at this time
	/// \return the number of open connections
	public native int NumberOfConnections();

	/// Sets the password incoming connections must match in the call to Connect (defaults to none). Pass 0 to passwordData to specify no password
	/// This is a way to set a low level password for all incoming connections.  To selectively reject connections, implement your own scheme using CloseConnection() to remove unwanted connections
	/// \param[in] passwordData A data block that incoming connections must match.  This can be just a password, or can be a stream of data. Specify 0 for no password data
	/// \param[in] passwordDataLength The length in bytes of passwordData
	public native void SetIncomingPassword( byte [] passwordData );

	/// Gets the password passed to SetIncomingPassword
	/// \param[out] passwordData  Should point to a block large enough to hold the password data you passed to SetIncomingPassword()
	/// \param[in,out] passwordDataLength Maximum size of the array passwordData.  Modified to hold the number of bytes actually written
	public native void GetIncomingPassword( byte [] passwordData );

	/// \brief Connect to the specified host (ip or domain name) and server port.
	/// Calling Connect and not calling SetMaximumIncomingConnections acts as a dedicated client.
	/// Calling both acts as a true peer. This is a non-blocking connection.
	/// You know the connection is successful when GetConnectionState() returns IS_CONNECTED or Receive() gets a message with the type identifier ID_CONNECTION_REQUEST_ACCEPTED.
	/// If the connection is not successful, such as a rejected connection or no response then neither of these things will happen.
	/// \pre Requires that you first call Startup()
	/// \param[in] host Either a dotted IP address or a domain name
	/// \param[in] remotePort Which port to connect to on the remote machine.
	/// \param[in] passwordData A data block that must match the data block on the server passed to SetIncomingPassword.  This can be a string or can be a stream of data.  Use 0 for no password.
	/// \param[in] passwordDataLength The length in bytes of passwordData
	/// \param[in] publicKey The public key the server is using. If 0, the server is not using security. If non-zero, the publicKeyMode member determines how to connect
	/// \param[in] connectionSocketIndex Index into the array of socket descriptors passed to socketDescriptors in RakPeer::Startup() to send on.
	/// \param[in] sendConnectionAttemptCount How many datagrams to send to the other system to try to connect.
	/// \param[in] timeBetweenSendConnectionAttemptsMS Time to elapse before a datagram is sent to the other system to try to connect. After sendConnectionAttemptCount number of attempts, ID_CONNECTION_ATTEMPT_FAILED is returned. Under low bandwidth conditions with multiple simultaneous outgoing connections, this value should be raised to 1000 or higher, or else the MTU detection can overrun the available bandwidth.
	/// \param[in] timeoutTime How long to keep the connection alive before dropping it on unable to send a reliable message. 0 to use the default from SetTimeoutTime(UNASSIGNED_SYSTEM_ADDRESS);
	/// \return CONNECTION_ATTEMPT_STARTED on successful initiation. Otherwise, an appropriate enumeration indicating failure.
	/// \note CONNECTION_ATTEMPT_STARTED does not mean you are already connected!
	/// \note It is possible to immediately get back ID_CONNECTION_ATTEMPT_FAILED if you exceed the maxConnections parameter passed to Startup(). This could happen if you call CloseConnection() with sendDisconnectionNotificaiton true, then immediately call Connect() before the connection has closed.
	public native ConnectionAttemptResult Connect(String host, int remotePort, byte [] passwordData, PublicKey publicKey, int connectionSocketIndex, int sendConnectionAttemptCount, int timeBetweenSendConnectionAttemptsMS, int timeoutTime );
	public ConnectionAttemptResult Connect(String host, int remotePort, byte [] passwordData)
	{
		return Connect(host, remotePort, passwordData, null, 0, 12, 500, 0);
	}

	/// \brief Connect to the specified host (ip or domain name) and server port, using a shared socket from another instance of RakNet
	/// \param[in] host Either a dotted IP address or a domain name
	/// \param[in] remotePort Which port to connect to on the remote machine.
	/// \param[in] passwordData A data block that must match the data block on the server passed to SetIncomingPassword.  This can be a string or can be a stream of data.  Use 0 for no password.
	/// \param[in] passwordDataLength The length in bytes of passwordData
	/// \param[in] socket A bound socket returned by another instance of RakPeerInterface
	/// \param[in] sendConnectionAttemptCount How many datagrams to send to the other system to try to connect.
	/// \param[in] timeBetweenSendConnectionAttemptsMS Time to elapse before a datagram is sent to the other system to try to connect. After sendConnectionAttemptCount number of attempts, ID_CONNECTION_ATTEMPT_FAILED is returned. Under low bandwidth conditions with multiple simultaneous outgoing connections, this value should be raised to 1000 or higher, or else the MTU detection can overrun the available bandwidth.
	/// \param[in] timeoutTime How long to keep the connection alive before dropping it on unable to send a reliable message. 0 to use the default from SetTimeoutTime(UNASSIGNED_SYSTEM_ADDRESS);
	/// \return CONNECTION_ATTEMPT_STARTED on successful initiation. Otherwise, an appropriate enumeration indicating failure.
	/// \note CONNECTION_ATTEMPT_STARTED does not mean you are already connected!
	public native ConnectionAttemptResult ConnectWithSocket(String host, int remotePort, byte [] passwordData, RakNetSocket2 socket, PublicKey publicKey, int sendConnectionAttemptCount, int timeBetweenSendConnectionAttemptsMS, int timeoutTime);
	public ConnectionAttemptResult ConnectWithSocket(String host, int remotePort, byte [] passwordData, RakNetSocket2 socket)
	{
		return ConnectWithSocket(host, remotePort, passwordData, socket, null, 12, 500, 0);
	}

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
