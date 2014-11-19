package com.spireofbabel.raknet4j.RakNetSocket2;

/**
 * Created by bcarson on 11/11/14.
 */
abstract public class RNS2EventHandler
{
	abstract public void OnRNS2Recv(RecvStruct recvStruct);
	abstract public void DeallocRNS2RecvStruct(RecvStruct s, String file, int line);
	abstract public RecvStruct AllocRNS2RecvStruct(String file, int line);
}
