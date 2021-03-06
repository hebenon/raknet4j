package com.spireofbabel.raknet4j;

import com.spireofbabel.raknet4j.RakNetEnums.PacketPriority;
import com.spireofbabel.raknet4j.RakNetEnums.PacketReliability;
import com.spireofbabel.raknet4j.RakNetEnums.StartupResult;
import com.spireofbabel.raknet4j.RakNetEnums.ConnectionAttemptResult;
import com.spireofbabel.raknet4j.RakNetSocket2.RakNetSocket2;
import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 8/11/14.
 */

public class RakPeerInterface {
    static {
		LibraryUtility.loadNativeLibrary("RakNetNatives");
    }

    private NativeHandle nativeHandle;

    private RakPeerInterface(NativeHandle handle) {
        nativeHandle = handle;
    }

    public static RakPeerInterface GetInstance() {
        return new RakPeerInterface(nativeGetInstance());
    }

    public static void DestroyInstance(RakPeerInterface i) {
        nativeDestroyInstance(i.nativeHandle);
    }

    // Native functions
    private static native NativeHandle nativeGetInstance();
    private static native void nativeDestroyInstance(NativeHandle i);

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

	/// Returns the next uint32_t that Send() will return
	/// \note If using RakPeer from multiple threads, this may not be accurate for your thread. Use IncrementNextSendReceipt() in that case.
	/// \return The next uint32_t that Send() or SendList will return
	public native long GetNextSendReceipt();

	/// Returns the next uint32_t that Send() will return, and increments the value by one
	/// \note If using RakPeer from multiple threads, pass this to forceReceipt in the send function
	/// \return The next uint32_t that Send() or SendList will return
	public native long IncrementNextSendReceipt();

	/// Sends a block of data to the specified system that you are connected to.
	/// This function only works while connected
	/// The first byte should be a message identifier starting at ID_USER_PACKET_ENUM
	/// \param[in] data The block of data to send
	/// \param[in] length The size in bytes of the data to send
	/// \param[in] priority What priority level to send on.  See PacketPriority.h
	/// \param[in] reliability How reliability to send this data.  See PacketPriority.h
	/// \param[in] orderingChannel When using ordered or sequenced messages, what channel to order these on. Messages are only ordered relative to other messages on the same stream
	/// \param[in] systemIdentifier Who to send this packet to, or in the case of broadcasting who not to send it to.  Pass either a SystemAddress structure or a RakNetGUID structure. Use UNASSIGNED_SYSTEM_ADDRESS or to specify none
	/// \param[in] broadcast True to send this packet to all connected systems. If true, then systemAddress specifies who not to send the packet to.
	/// \param[in] forceReceipt If 0, will automatically determine the receipt number to return. If non-zero, will return what you give it.
	/// \return 0 on bad input. Otherwise a number that identifies this message. If \a reliability is a type that returns a receipt, on a later call to Receive() you will get ID_SND_RECEIPT_ACKED or ID_SND_RECEIPT_LOSS with bytes 1-4 inclusive containing this number
	public native long Send( byte [] data, PacketPriority priority, PacketReliability reliability, int orderingChannel, AddressOrGUID systemIdentifier, boolean broadcast, long forceReceiptNumber );
	public long Send( byte [] data, PacketPriority priority, PacketReliability reliability, int orderingChannel, AddressOrGUID systemIdentifier, boolean broadcast) {
		return Send(data, priority, reliability, orderingChannel, systemIdentifier, broadcast, 0);
	}

	/// "Send" to yourself rather than a remote system. The message will be processed through the plugins and returned to the game as usual
	/// This function works anytime
	/// The first byte should be a message identifier starting at ID_USER_PACKET_ENUM
	/// \param[in] data The block of data to send
	/// \param[in] length The size in bytes of the data to send
	public native void SendLoopback( byte [] data );

	/// Sends a block of data to the specified system that you are connected to.  Same as the above version, but takes a BitStream as input.
	/// \param[in] bitStream The bitstream to send
	/// \param[in] priority What priority level to send on.  See PacketPriority.h
	/// \param[in] reliability How reliability to send this data.  See PacketPriority.h
	/// \param[in] orderingChannel When using ordered or sequenced messages, what channel to order these on. Messages are only ordered relative to other messages on the same stream
	/// \param[in] systemIdentifier Who to send this packet to, or in the case of broadcasting who not to send it to. Pass either a SystemAddress structure or a RakNetGUID structure. Use UNASSIGNED_SYSTEM_ADDRESS or to specify none
	/// \param[in] broadcast True to send this packet to all connected systems. If true, then systemAddress specifies who not to send the packet to.
	/// \param[in] forceReceipt If 0, will automatically determine the receipt number to return. If non-zero, will return what you give it.
	/// \return 0 on bad input. Otherwise a number that identifies this message. If \a reliability is a type that returns a receipt, on a later call to Receive() you will get ID_SND_RECEIPT_ACKED or ID_SND_RECEIPT_LOSS with bytes 1-4 inclusive containing this number
	/// \note COMMON MISTAKE: When writing the first byte, bitStream->Write((unsigned char) ID_MY_TYPE) be sure it is casted to a byte, and you are not writing a 4 byte enumeration.
	public native long Send( BitStream bitStream, PacketPriority priority, PacketReliability reliability, int orderingChannel, AddressOrGUID systemIdentifier, boolean broadcast, long forceReceiptNumber );
	public long Send( BitStream bitStream, PacketPriority priority, PacketReliability reliability, int orderingChannel, AddressOrGUID systemIdentifier, boolean broadcast ) {
		return Send(bitStream, priority, reliability, orderingChannel, systemIdentifier, broadcast, 0 );
	}

	/// Sends multiple blocks of data, concatenating them automatically.
	///
	/// This is equivalent to:
	/// RakNet::BitStream bs;
	/// bs.WriteAlignedBytes(block1, blockLength1);
	/// bs.WriteAlignedBytes(block2, blockLength2);
	/// bs.WriteAlignedBytes(block3, blockLength3);
	/// Send(&bs, ...)
	///
	/// This function only works while connected
	/// \param[in] data An array of pointers to blocks of data
	/// \param[in] lengths An array of integers indicating the length of each block of data
	/// \param[in] numParameters Length of the arrays data and lengths
	/// \param[in] priority What priority level to send on.  See PacketPriority.h
	/// \param[in] reliability How reliability to send this data.  See PacketPriority.h
	/// \param[in] orderingChannel When using ordered or sequenced messages, what channel to order these on. Messages are only ordered relative to other messages on the same stream
	/// \param[in] systemIdentifier Who to send this packet to, or in the case of broadcasting who not to send it to. Pass either a SystemAddress structure or a RakNetGUID structure. Use UNASSIGNED_SYSTEM_ADDRESS or to specify none
	/// \param[in] broadcast True to send this packet to all connected systems. If true, then systemAddress specifies who not to send the packet to.
	/// \param[in] forceReceipt If 0, will automatically determine the receipt number to return. If non-zero, will return what you give it.
	/// \return 0 on bad input. Otherwise a number that identifies this message. If \a reliability is a type that returns a receipt, on a later call to Receive() you will get ID_SND_RECEIPT_ACKED or ID_SND_RECEIPT_LOSS with bytes 1-4 inclusive containing this number
	public native long SendList( byte [][] data, PacketPriority priority, PacketReliability reliability, int orderingChannel, AddressOrGUID systemIdentifier, boolean broadcast, long forceReceiptNumber );
	public long SendList( byte [][] data, PacketPriority priority, PacketReliability reliability, int orderingChannel, AddressOrGUID systemIdentifier, boolean broadcast ) {
		return SendList( data, priority, reliability, orderingChannel, systemIdentifier, broadcast, 0);
	}

	/// Gets a message from the incoming message queue.
	/// Use DeallocatePacket() to deallocate the message after you are done with it.
	/// User-thread functions, such as RPC calls and the plugin function PluginInterface::Update occur here.
	/// \return 0 if no packets are waiting to be handled, otherwise a pointer to a packet.
	/// \note COMMON MISTAKE: Be sure to call this in a loop, once per game tick, until it returns 0. If you only process one packet per game tick they will buffer up.
	/// sa RakNetTypes.h contains struct Packet
	public native Packet Receive();

	/// Call this to deallocate a message returned by Receive() when you are done handling it.
	/// \param[in] packet The message to deallocate.
	public native void DeallocatePacket( Packet packet );

	// -------------------------------------------------------------------------------------------- Plugin Functions--------------------------------------------------------------------------------------------
	/// \brief Attaches a Plugin interface to an instance of the base class (RakPeer or PacketizedTCP) to run code automatically on message receipt in the Receive call.
	/// If the plugin returns false from PluginInterface::UsesReliabilityLayer(), which is the case for all plugins except PacketLogger, you can call AttachPlugin() and DetachPlugin() for this plugin while RakPeer is active.
	/// \param[in] messageHandler Pointer to the plugin to attach.
	public native void AttachPlugin( PluginInterface2 plugin );

	/// \brief Detaches a Plugin interface from the instance of the base class (RakPeer or PacketizedTCP) it is attached to.
	///	\details This method disables the plugin code from running automatically on base class's updates or message receipt.
	/// If the plugin returns false from PluginInterface::UsesReliabilityLayer(), which is the case for all plugins except PacketLogger, you can call AttachPlugin() and DetachPlugin() for this plugin while RakPeer is active.
	/// \param[in] messageHandler Pointer to a plugin to detach.
	public native void DetachPlugin( PluginInterface2 messageHandler );
}
