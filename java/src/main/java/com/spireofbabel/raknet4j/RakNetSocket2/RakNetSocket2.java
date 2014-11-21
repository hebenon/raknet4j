package com.spireofbabel.raknet4j.RakNetSocket2;

/**
 * Created by bcarson on 11/11/14.
 */

public class RakNetSocket2
{
    enum RNS2BindResult
    {
    	BR_SUCCESS,
    	BR_REQUIRES_RAKNET_SUPPORT_IPV6_DEFINE,
    	BR_FAILED_TO_BIND_SOCKET,
    	BR_FAILED_SEND_TEST,
    }

    enum RNS2Type
    {
    	RNS2T_WINDOWS_STORE_8,
    	RNS2T_PS3,
    	RNS2T_PS4,
    	RNS2T_CHROME,
    	RNS2T_VITA,
    	RNS2T_XBOX_360,
    	RNS2T_XBOX_720,
    	RNS2T_WINDOWS,
    	RNS2T_LINUX
    }

	// In order for the handler to trigger, some platforms must call PollRecvFrom, some platforms this create an internal thread.
	public native void SetRecvEventHandler(RNS2EventHandler eventHandler);
	public native int Send( SendParameters [] sendParameters, String file, int line );
	public native RNS2Type GetSocketType();
	public native void SetSocketType(RNS2Type t);
	public native boolean IsBerkleySocket();
	public native SystemAddress GetBoundAddress();
	public native int GetUserConnectionSocketIndex();
	public native void SetUserConnectionSocketIndex(int i);
	public native RNS2EventHandler GetEventHandler();

	// ----------- STATICS ------------
    // Of questionable value - Java can probably provide some of these.
	public static native void GetMyIP( SystemAddress [] addresses );
	public static native void DomainNameToIP( String domainName, String [] ip );

};
