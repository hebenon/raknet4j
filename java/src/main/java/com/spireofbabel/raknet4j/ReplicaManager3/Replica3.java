package com.spireofbabel.raknet4j.ReplicaManager3;

import com.spireofbabel.raknet4j.BitStream;
import com.spireofbabel.raknet4j.NativeHandle;
import com.spireofbabel.raknet4j.RakNetGUID;
import com.spireofbabel.raknet4j.RakPeerInterface;

/**
 * Created by bcarson on 11/11/14.
 */
abstract class Replica3 {
	static {
		 String libPath = Replica3.class.getClassLoader().getResource("lib/libRakNetNatives.dylib").getPath();
		 System.load(libPath);
	}

	NativeHandle nativeHandle;

	public Replica3() {
		nativeHandle = nativeReplica3();
	}
	public Replica3(NativeHandle _handle) { nativeHandle = _handle; }

	private native NativeHandle nativeReplica3();
	private static native void nativeDestroyInstance(NativeHandle handle);

	/// \brief Return codes for Connection_RM3::GetConstructionState() and Replica3::QueryConstruction()
	/// \details Indicates what state the object should be in for the remote system
	/// \ingroup REPLICA_MANAGER_GROUP3
	enum RM3ConstructionState
	{
		/// This object should exist on the remote system. Send a construction message if necessary
		/// If the NetworkID is already in use, it will not do anything
		/// If it is not in use, it will create the object, and then call DeserializeConstruction
		RM3CS_SEND_CONSTRUCTION,

		/// This object should exist on the remote system.
		/// The other system already has the object, and the object will never be deleted.
		/// This is true of objects that are loaded with the level, for example.
		/// Treat it as if it existed, without sending a construction message.
		/// Will call Serialize() and SerializeConstructionExisting() to the object on the remote system
		RM3CS_ALREADY_EXISTS_REMOTELY,

		/// Same as RM3CS_ALREADY_EXISTS_REMOTELY but does not call SerializeConstructionExisting()
		RM3CS_ALREADY_EXISTS_REMOTELY_DO_NOT_CONSTRUCT,

		/// This object will never be sent to the target system
		/// This object will never be serialized from this system to the target system
		RM3CS_NEVER_CONSTRUCT,

		/// Don't do anything this tick. Will query again next tick
		RM3CS_NO_ACTION,

		/// Max enum
		RM3CS_MAX,
	}

	/// If this object already exists for this system, should it be removed?
	/// \ingroup REPLICA_MANAGER_GROUP3
	enum RM3DestructionState
	{
		/// This object should not exist on the remote system. Send a destruction message if necessary.
		RM3DS_SEND_DESTRUCTION,

		/// This object will never be destroyed by a per-tick query. Don't call again
		RM3DS_DO_NOT_QUERY_DESTRUCTION,

		/// Don't do anything this tick. Will query again next tick
		RM3DS_NO_ACTION,

		/// Max enum
		RM3DS_MAX,
	}

	/// Return codes when constructing an object
	/// \ingroup REPLICA_MANAGER_GROUP3
	enum RM3SerializationResult
	{
		/// This object serializes identically no matter who we send to
		/// We also send it to every connection (broadcast).
		/// Efficient for memory, speed, and bandwidth but only if the object is always broadcast identically.
		RM3SR_BROADCAST_IDENTICALLY,

		/// Same as RM3SR_BROADCAST_IDENTICALLY, but assume the object needs to be serialized, do not check with a memcmp
		/// Assume the object changed, and serialize it
		/// Use this if you know exactly when your object needs to change. Can be faster than RM3SR_BROADCAST_IDENTICALLY.
		/// An example of this is if every member variable has an accessor, changing a member sets a flag, and you check that flag in Replica3::QuerySerialization()
		/// The opposite of this is RM3SR_DO_NOT_SERIALIZE, in case the object did not change
		RM3SR_BROADCAST_IDENTICALLY_FORCE_SERIALIZATION,

		/// Either this object serializes differently depending on who we send to or we send it to some systems and not others.
		/// Inefficient for memory and speed, but efficient for bandwidth
		/// However, if you don't know what to return, return this
		RM3SR_SERIALIZED_UNIQUELY,

		/// Do not compare against last sent value. Just send even if the data is the same as the last tick
		/// If the data is always changing anyway, or you want to send unreliably, this is a good method of serialization
		/// Can send unique data per connection if desired. If same data is sent to all connections, use RM3SR_SERIALIZED_ALWAYS_IDENTICALLY for even better performance
		/// Efficient for memory and speed, but not necessarily bandwidth
		RM3SR_SERIALIZED_ALWAYS,

		/// \deprecated, use RM3SR_BROADCAST_IDENTICALLY_FORCE_SERIALIZATION
		RM3SR_SERIALIZED_ALWAYS_IDENTICALLY,

		/// Do not serialize this object this tick, for this connection. Will query again next autoserialize timer
		RM3SR_DO_NOT_SERIALIZE,

		/// Never serialize this object for this connection
		/// Useful for objects that are downloaded, and never change again
		/// Efficient
		RM3SR_NEVER_SERIALIZE_FOR_THIS_CONNECTION,

		/// Max enum
		RM3SR_MAX,
	}

	/// First pass at topology to see if an object should be serialized
	/// \ingroup REPLICA_MANAGER_GROUP3
	enum RM3QuerySerializationResult
	{
		/// Call Serialize() to see if this object should be serializable for this connection
		RM3QSR_CALL_SERIALIZE,
		/// Do not call Serialize() this tick to see if this object should be serializable for this connection
		RM3QSR_DO_NOT_CALL_SERIALIZE,
		/// Never call Serialize() for this object and connection. This system will not serialize this object for this topology
		RM3QSR_NEVER_CALL_SERIALIZE,
		/// Max enum
		RM3QSR_MAX,
	}

	/// \ingroup REPLICA_MANAGER_GROUP3
	enum RM3ActionOnPopConnection
	{
		RM3AOPC_DO_NOTHING,
		RM3AOPC_DELETE_REPLICA,
		RM3AOPC_DELETE_REPLICA_AND_BROADCAST_DESTRUCTION,
		RM3AOPC_MAX,
	}

	/// \ingroup REPLICA_MANAGER_GROUP3
	/// Used for Replica3::QueryConstruction_PeerToPeer() and Replica3::QuerySerialization_PeerToPeer() to describe how the object replicates between hosts
	enum Replica3P2PMode
	{
		/// The Replica3 instance is constructed and serialized by one system only.
		/// Example: Your avatar. No other player serializes or can create your avatar.
		R3P2PM_SINGLE_OWNER,
		/// The Replica3 instance is constructed and/or serialized by different systems
		/// This system is currently in charge of construction and/or serialization
		/// Example: A pickup. When an avatar holds it, that avatar controls it. When it is on the ground, the host controls it.
		R3P2PM_MULTI_OWNER_CURRENTLY_AUTHORITATIVE,
		/// The Replica3 instance is constructed and/or serialized by different systems
		/// Another system is in charge of construction and/or serialization, but this system may be in charge at a later time
		/// Example: A pickup held by another player. That player sends creation of that object to new connections, and serializes it until it is dropped.
		R3P2PM_MULTI_OWNER_NOT_CURRENTLY_AUTHORITATIVE,
		/// The Replica3 instance is a static object (already exists on the remote system).
		/// This system is currently in charge of construction and/or serialization
		R3P2PM_STATIC_OBJECT_CURRENTLY_AUTHORITATIVE,
		/// The Replica3 instance is a static object (already exists on the remote system).
		/// Another system is in charge of construction and/or serialization, but this system may be in charge at a later time
		R3P2PM_STATIC_OBJECT_NOT_CURRENTLY_AUTHORITATIVE,

	}

	/// \brief Write a unique identifer that can be read on a remote system to create an object of this same class.
	/// \details The value written to \a allocationIdBitstream will be passed to Connection_RM3::AllocReplica().<BR>
	/// Sample implementation:<BR>
	/// {allocationIdBitstream->Write(RakNet::RakString("Soldier");}<BR>
	/// \param[out] allocationIdBitstream Bitstream for the user to write to, to identify this class
	public abstract void WriteAllocationID(Connection_RM3 destinationConnection, BitStream allocationIdBitstream);

	/// \brief Ask if this object, which does not exist on \a destinationConnection should (now) be sent to that system.
	/// \details If ReplicaManager3::QueryConstructionMode() returns QUERY_CONNECTION_FOR_REPLICA_LIST or QUERY_REPLICA_FOR_CONSTRUCTION_AND_DESTRUCTION (default),
	/// then QueyrConstruction() is called once per tick from ReplicaManager3::Update() to determine if an object should exist on a given system.<BR>
	/// Based on the return value, a network message may be sent to the other system to create the object.<BR>
	/// If QueryConstructionMode() is overriden to return QUERY_CONNECTION_FOR_REPLICA_LIST, this function is unused.<BR>
	/// \note Defaults are provided: QueryConstruction_PeerToPeer(), QueryConstruction_ServerConstruction(), QueryConstruction_ClientConstruction(). Return one of these functions for a working default for the relevant topology.
	/// \param[in] destinationConnection Which system we will send to
	/// \param[in] replicaManager3 Plugin instance for this Replica3
	/// \return What action to take
	public abstract RM3ConstructionState QueryConstruction(Connection_RM3 destinationConnection, ReplicaManager3 replicaManager3);

	/// \brief Ask if this object, which does exist on \a destinationConnection should be removed from the remote system
	/// \details If ReplicaManager3::QueryConstructionMode() returns QUERY_REPLICA_FOR_CONSTRUCTION_AND_DESTRUCTION (default),
	/// then QueryDestruction() is called once per tick from ReplicaManager3::Update() to determine if an object that exists on a remote system should be destroyed for a given system.<BR>
	/// Based on the return value, a network message may be sent to the other system to destroy the object.<BR>
	/// Note that you can also destroy objects with BroadcastDestruction(), so this function is not useful unless you plan to delete objects for only a particular connection.<BR>
	/// If QueryConstructionMode() is overriden to return QUERY_CONNECTION_FOR_REPLICA_LIST, this function is unused.<BR>
	/// \param[in] destinationConnection Which system we will send to
	/// \param[in] replicaManager3 Plugin instance for this Replica3
	/// \return What action to take. Only RM3CS_SEND_DESTRUCTION does anything at this time.
	public RM3DestructionState QueryDestruction(Connection_RM3 destinationConnection, ReplicaManager3 replicaManager3) {
        return RM3DestructionState.RM3DS_DO_NOT_QUERY_DESTRUCTION;
    }

	/// \brief We're about to call DeserializeConstruction() on this Replica3. If QueryRemoteConstruction() returns false, this object is deleted instead.
	/// \details By default, QueryRemoteConstruction_ServerConstruction() does not allow clients to create objects. The client will get Replica3::DeserializeConstructionRequestRejected().<BR>
	/// If you want the client to be able to potentially create objects for client/server, override accordingly.<BR>
	/// Other variants of QueryRemoteConstruction_* just return true.
	/// \note Defaults are provided: QueryRemoteConstruction_PeerToPeer(), QueryRemoteConstruction_ServerConstruction(), QueryRemoteConstruction_ClientConstruction(). Return one of these functions for a working default for the relevant topology.
	/// \param[in] sourceConnection Which system sent us the object creation request message.
	/// \return True to allow the object to pass onto DeserializeConstruction() (where it may also be rejected), false to immediately reject the remote construction request
	public abstract boolean QueryRemoteConstruction(Connection_RM3 sourceConnection);

	/// \brief We got a message from a connection to destroy this replica
	/// Return true to automatically relay the destruction message to all our other connections
	/// For a client in client/server, it does not matter what this funtion returns
	/// For a server in client/server, this should normally return true
	/// For a peer in peer to peer, you can normally return false since the original destroying peer would have told all other peers about the destruction
	/// If a system gets a destruction command for an object that was already destroyed, the destruction message is ignored
	public boolean QueryRelayDestruction(Connection_RM3 sourceConnection) {
        return true;
    }

	/// \brief Write data to be sent only when the object is constructed on a remote system.
	/// \details SerializeConstruction is used to write out data that you need to create this object in the context of your game, such as health, score, name. Use it for data you only need to send when the object is created.<BR>
	/// After SerializeConstruction() is called, Serialize() will be called immediately thereafter. However, they are sent in different messages, so Serialize() may arrive a later frame than SerializeConstruction()
	/// For that reason, the object should be valid after a call to DeserializeConstruction() for at least a short time.<BR>
	/// \note The object's NetworkID and allocation id are handled by the system automatically, you do not need to write these values to \a constructionBitstream
	/// \param[out] constructionBitstream Destination bitstream to write your data to
	/// \param[in] destinationConnection System that will receive this network message.
	public abstract void SerializeConstruction(BitStream constructionBitstream, Connection_RM3 destinationConnection);

	/// \brief Read data written by Replica3::SerializeConstruction()
	/// \details Reads whatever data was written to \a constructionBitstream in Replica3::SerializeConstruction()
	/// \param[out] constructionBitstream Bitstream written to in Replica3::SerializeConstruction()
	/// \param[in] sourceConnection System that sent us this network message.
	/// \return true to accept construction of the object. false to reject, in which case the object will be deleted via Replica3::DeallocReplica()
	public abstract boolean DeserializeConstruction(BitStream constructionBitstream, Connection_RM3 sourceConnection);

	/// Same as SerializeConstruction(), but for an object that already exists on the remote system.
	/// Used if you return RM3CS_ALREADY_EXISTS_REMOTELY from QueryConstruction
	public void SerializeConstructionExisting(BitStream constructionBitstream, Connection_RM3 destinationConnection) {}

	/// Same as DeserializeConstruction(), but for an object that already exists on the remote system.
	/// Used if you return RM3CS_ALREADY_EXISTS_REMOTELY from QueryConstruction
	public void DeserializeConstructionExisting(BitStream constructionBitstream, Connection_RM3 sourceConnection) {}

	/// \brief Write extra data to send with the object deletion event, if desired
	/// \details Replica3::SerializeDestruction() will be called to write any object destruction specific data you want to send with this event.
	/// \a destructionBitstream can be read in DeserializeDestruction()
	/// \param[out] destructionBitstream Bitstream for you to write to
	/// \param[in] destinationConnection System that will receive this network message.
	public abstract void SerializeDestruction(BitStream destructionBitstream, Connection_RM3 destinationConnection);

	/// \brief Read data written by Replica3::SerializeDestruction()
	/// \details Return true to delete the object. BroadcastDestruction() will be called automatically, followed by ReplicaManager3::Dereference.<BR>
	/// Return false to not delete it. If you delete it at a later point, you are responsible for calling BroadcastDestruction() yourself.
	public abstract boolean DeserializeDestruction(BitStream destructionBitstream, Connection_RM3 sourceConnection);

	/// \brief The system is asking what to do with this replica when the connection is dropped
	/// \details Return QueryActionOnPopConnection_Client, QueryActionOnPopConnection_Server, or QueryActionOnPopConnection_PeerToPeer
	public abstract RM3ActionOnPopConnection QueryActionOnPopConnection(Connection_RM3 droppedConnection);

	/// Notification called for each of our replicas when a connection is popped
	public void OnPoppedConnection(Connection_RM3 droppedConnection) {}

	/// \brief Override with {delete this;}
	/// \details
	/// <OL>
	/// <LI>Got a remote message to delete this object which passed DeserializeDestruction(), OR
	/// <LI>ReplicaManager3::SetAutoManageConnections() was called autoDestroy true (which is the default setting), and a remote system that owns this object disconnected) OR
	/// <\OL>
	/// <BR>
	/// Override with {delete this;} to actually delete the object (and any other processing you wish).<BR>
	/// If you don't want to delete the object, just do nothing, however, the system will not know this. You may wish to call Dereference() if the object should no longer be networked, but remain in memory. You are responsible for deleting it yoruself later.<BR>
	/// destructionBitstream may be 0 if the object was deleted locally
	public abstract void DeallocReplica(Connection_RM3 sourceConnection);

	/// \brief Implement with QuerySerialization_ClientSerializable(), QuerySerialization_ServerSerializable(), or QuerySerialization_PeerToPeer()
	/// \details QuerySerialization() is a first pass query to check if a given object should serializable to a given system. The intent is that the user implements with one of the defaults for client, server, or peer to peer.<BR>
	/// Without this function, a careless implementation would serialize an object anytime it changed to all systems. This would give you feedback loops as the sender gets the same message back from the recipient it just sent to.<BR>
	/// If more than one system can serialize the same object then you will need to override to return true, and control the serialization result from Replica3::Serialize(). Be careful not to send back the same data to the system that just sent to you!
	/// \return True to allow calling Replica3::Serialize() for this connection, false to not call.
	public abstract RM3QuerySerializationResult QuerySerialization(Connection_RM3 destinationConnection);

	/// \brief Called for each replica owned by the user, once per Serialization tick, before Serialize() is called.
	/// If you want to do some kind of operation on the Replica objects that you own, just before Serialization(), then overload this function
	public void OnUserReplicaPreSerializeTick() {}

	/// \brief Serialize our class to a bitstream
	/// \details User should implement this function to write the contents of this class to SerializationParamters::serializationBitstream.<BR>
	/// If data only needs to be written once, you can write it to SerializeConstruction() instead for efficiency.<BR>
	/// Transmitted over the network if it changed from the last time we called Serialize().<BR>
	/// Called every time the time interval to ReplicaManager3::SetAutoSerializeInterval() elapses and ReplicaManager3::Update is subsequently called.
	/// \param[in/out] serializeParameters Parameters controlling the serialization, including destination bitstream to write to
	/// \return Whether to serialize, and if so, how to optimize the results
	public abstract RM3SerializationResult Serialize(SerializeParameters serializeParameters);

	/// \brief Called when the class is actually transmitted via Serialize()
	/// \details Use to track how much bandwidth this class it taking
	public void OnSerializeTransmission(BitStream bitStream, Connection_RM3 destinationConnection, long [] bitsPerChannel, long curTime) {}

	/// \brief Read what was written in Serialize()
	/// \details Reads the contents of the class from SerializationParamters::serializationBitstream.<BR>
	/// Called whenever Serialize() is called with different data from the last send.
	/// \param[in] serializationBitstream Bitstream passed to Serialize()
	/// \param[in] timeStamp 0 if unused, else contains the time the message originated on the remote system
	/// \param[in] sourceConnection Which system sent to us
	public abstract void Deserialize(DeserializeParameters deserializeParameters);

	/// \brief Called after SerializeConstruction completes for all objects in a given update tick.<BR>
	/// Writes to PostDeserializeConstruction(), which is called after all objects are created for a given Construction tick().
	/// Override to send data to PostDeserializeConstruction(), such as the NetworkID of other objects to resolve pointers to
	public void PostSerializeConstruction(BitStream constructionBitstream, Connection_RM3 destinationConnection) {}

	/// Called after DeserializeConstruction completes for all objects in a given update tick.<BR>
	/// This is used to resolve dependency chains, where two objects would refer to each other in DeserializeConstruction, yet one had not been constructed yet
	/// In PostDeserializeConstruction(), you know that all objects have already been created, so can resolve NetworkIDs to pointers safely.
	/// You can also use it to trigger some sort of event when you know the object has completed deserialization.
	/// \param[in] constructionBitstream BitStream written in PostSerializeConstruction()
	/// \param[in] sourceConnection System that sent us this network message.
	public void PostDeserializeConstruction(BitStream constructionBitstream, Connection_RM3 sourceConnection) {}

	/// Same as PostSerializeConstruction(), but for objects that returned RM3CS_ALREADY_EXISTS_REMOTELY from QueryConstruction
	public void PostSerializeConstructionExisting(BitStream constructionBitstream, Connection_RM3 destinationConnection) {}

	/// Same as PostDeserializeConstruction(), but for objects that returned RM3CS_ALREADY_EXISTS_REMOTELY from QueryConstruction
	public void PostDeserializeConstructionExisting(BitStream constructionBitstream, Connection_RM3 sourceConnection) {}

	/// Called after DeserializeDestruction completes for the object successfully, but obviously before the object is deleted.<BR>
	/// Override to trigger some sort of event when you know the object has completed destruction.
	/// \param[in] sourceConnection System that sent us this network message.
	public void PreDestruction(Connection_RM3 sourceConnection) {}

	/// \brief Default call for QueryConstruction().
	/// \details Both the client and the server is allowed to create this object. The network topology is client/server
	/// \param[in] destinationConnection destinationConnection parameter passed to QueryConstruction()
	/// \param[in] isThisTheServer True if this system is the server, false if not.
	public native RM3ConstructionState QueryConstruction_ClientConstruction(Connection_RM3 destinationConnection, boolean isThisTheServer);

	/// Default call for QueryRemoteConstruction().
	/// \details Both the client and the server is allowed to create this object. The network topology is client/server
	/// The code means on the client or the server, allow creation of Replica3 instances
	/// \param[in] sourceConnection destinationConnection parameter passed to QueryConstruction()
	/// \param[in] isThisTheServer True if this system is the server, false if not.
	public native boolean QueryRemoteConstruction_ClientConstruction(Connection_RM3 sourceConnection, boolean isThisTheServer);

	/// \brief Default call for QueryConstruction().
	/// \details Only the server is allowed to create this object. The network topology is client/server
	/// \param[in] destinationConnection destinationConnection parameter passed to QueryConstruction()
	/// \param[in] isThisTheServer True if this system is the server, false if not.
	public native RM3ConstructionState QueryConstruction_ServerConstruction(Connection_RM3 destinationConnection, boolean isThisTheServer);

	/// \brief Default call for QueryRemoteConstruction(). Allow the server to create this object, but not the client.
	/// \details Only the server is allowed to create this object. The network topology is client/server
	/// The code means if this is the server, and I got a command to create a Replica3 to ignore it. If this is the client, to allow it.
	/// \param[in] sourceConnection destinationConnection parameter passed to QueryConstruction()
	/// \param[in] isThisTheServer True if this system is the server, false if not.
	public native boolean QueryRemoteConstruction_ServerConstruction(Connection_RM3 sourceConnection, boolean isThisTheServer);

	/// \brief Default call for QueryConstruction().
	/// \details All clients are allowed to create all objects. The object is not relayed when remotely created
	/// \param[in] destinationConnection destinationConnection parameter passed to QueryConstruction()
	/// \param[in] p2pMode If controlled only by this system ever, pass R3P2PM_SINGLE_OWNER. Otherwise pass R3P2PM_MULTI_OWNER_CURRENTLY_AUTHORITATIVE or R3P2PM_MULTI_OWNER_NOT_CURRENTLY_AUTHORITATIVE
	/// p2pMode=R3P2PM_SINGLE_OWNER
	public native RM3ConstructionState QueryConstruction_PeerToPeer(Connection_RM3 destinationConnection, Replica3P2PMode p2pMode);

	/// \brief Default call for QueryRemoteConstruction().
	/// \details All clients are allowed to create all objects. The object is not relayed when remotely created
	/// \param[in] sourceConnection destinationConnection parameter passed to QueryConstruction()
	public native boolean QueryRemoteConstruction_PeerToPeer(Connection_RM3 sourceConnection);

	/// \brief Default call for QuerySerialization().
	/// \details Use if the values you are serializing are generated by the client that owns the object. The serialization will be relayed through the server to the other clients.
	/// \param[in] destinationConnection destinationConnection parameter passed to QueryConstruction()
	/// \param[in] isThisTheServer True if this system is the server, false if not.
	public native RM3QuerySerializationResult QuerySerialization_ClientSerializable(Connection_RM3 destinationConnection, boolean isThisTheServer);
	/// \brief Default call for QuerySerialization().
	/// \details Use if the values you are serializing are generated only by the server. The serialization will be sent to all clients, but the clients will not send back to the server.
	/// \param[in] destinationConnection destinationConnection parameter passed to QueryConstruction()
	/// \param[in] isThisTheServer True if this system is the server, false if not.
	public native RM3QuerySerializationResult QuerySerialization_ServerSerializable(Connection_RM3 destinationConnection, boolean isThisTheServer);
	/// \brief Default call for QuerySerialization().
	/// \details Use if the values you are serializing are on a peer to peer network. The peer that owns the object will send to all. Remote peers will not send.
	/// \param[in] destinationConnection destinationConnection parameter passed to QueryConstruction()
	/// \param[in] p2pMode If controlled only by this system ever, pass R3P2PM_SINGLE_OWNER. Otherwise pass R3P2PM_MULTI_OWNER_CURRENTLY_AUTHORITATIVE or R3P2PM_MULTI_OWNER_NOT_CURRENTLY_AUTHORITATIVE
	/// p2pMode=R3P2PM_SINGLE_OWNER
	public native RM3QuerySerializationResult QuerySerialization_PeerToPeer(Connection_RM3 destinationConnection, Replica3P2PMode p2pMode);

	/// Default: If we are a client, and the connection is lost, delete the server's objects
	public abstract RM3ActionOnPopConnection QueryActionOnPopConnection_Client(Connection_RM3 droppedConnection);
	/// Default: If we are a server, and the connection is lost, delete the client's objects and broadcast the destruction
	public abstract RM3ActionOnPopConnection QueryActionOnPopConnection_Server(Connection_RM3 droppedConnection);
	/// Default: If we are a peer, and the connection is lost, delete the peer's objects
	public abstract RM3ActionOnPopConnection QueryActionOnPopConnection_PeerToPeer(Connection_RM3 droppedConnection);

	/// Call to send a network message to delete this object on other systems.<BR>
	/// Call it before deleting the object
	public native void BroadcastDestruction();

	/// creatingSystemGUID is set the first time Reference() is called, or if we get the object from another system
	/// \return System that originally created this object
	public native RakNetGUID GetCreatingSystemGUID();

    /// \return If ReplicaManager3::Reference() was called on this object.
	public native boolean WasReferenced();
}
