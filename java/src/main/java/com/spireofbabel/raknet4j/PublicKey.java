package com.spireofbabel.raknet4j;

import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 11/11/14.
 */

public class PublicKey {
    static {
        LibraryUtility.loadNativeLibrary("RakNetNatives");
    }

    private NativeHandle nativeHandle;

    public PublicKey() {
        nativeHandle = nativePublicKey();
    }

    public PublicKey(NativeHandle _nativeHandle) {
        nativeHandle = _nativeHandle;
    }

    private native NativeHandle nativePublicKey();

    /// Used with the PublicKey structure
    enum PublicKeyMode
    {
        /// The connection is insecure. You can also just pass 0 for the pointer to PublicKey in RakPeerInterface::Connect()
        PKM_INSECURE_CONNECTION,

        /// Accept whatever public key the server gives us. This is vulnerable to man in the middle, but does not require
        /// distribution of the public key in advance of connecting.
        PKM_ACCEPT_ANY_PUBLIC_KEY,

        /// Use a known remote server public key. PublicKey::remoteServerPublicKey must be non-zero.
        /// This is the recommended mode for secure connections.
        PKM_USE_KNOWN_PUBLIC_KEY,

        /// Use a known remote server public key AND provide a public key for the connecting client.
        /// PublicKey::remoteServerPublicKey, myPublicKey and myPrivateKey must be all be non-zero.
        /// The server must cooperate for this mode to work.
        /// I recommend not using this mode except for server-to-server communication as it significantly increases the CPU requirements during connections for both sides.
        /// Furthermore, when it is used, a connection password should be used as well to avoid DoS attacks.
        PKM_USE_TWO_WAY_AUTHENTICATION
    }

	/// How to interpret the public key, see above
    public native PublicKeyMode getPublicKeyMode();
	public native void setPublicKeyMode(PublicKeyMode publicKeyMode);

	/// Pointer to a public key of length cat::EasyHandshake::PUBLIC_KEY_BYTES. See the Encryption sample.
	public native byte [] getRemoteServerPublicKey();
    public native void setRemoteServerPublicKey(byte [] remoteServerPublicKey);

	/// (Optional) Pointer to a public key of length cat::EasyHandshake::PUBLIC_KEY_BYTES
	public native byte [] getMyPublicKey();
    public native void setMyPublicKey(byte [] myPublicKey);

	/// (Optional) Pointer to a private key of length cat::EasyHandshake::PRIVATE_KEY_BYTES
	public native byte [] getMyPrivateKey();
    public native void setMyPrivateKey(byte [] myPrivateKey);
}

