package com.spireofbabel.raknet4j.ReplicaManager3;

import com.spireofbabel.raknet4j.BitStream;
import com.spireofbabel.raknet4j.NativeHandle;
import com.spireofbabel.raknet4j.RakNetGUID;
import com.spireofbabel.raknet4j.RakNetSocket2.SystemAddress;

import java.util.List;

/**
 * Created by bcarson on 13/11/14.
 */
abstract public class Connection_RM3 {
    private NativeHandle nativeHandle;

    public Connection_RM3(SystemAddress _systemAddress, RakNetGUID _guid) {
        nativeHandle = nativeConnection_RM3(_systemAddress, _guid);
    }

    public Connection_RM3(NativeHandle _handle) { nativeHandle = _handle; }

    private native NativeHandle nativeConnection_RM3(SystemAddress _systemAddress, RakNetGUID _guid);
    public native static void nativeDestroyInstance(NativeHandle handle);

    /// \brief Class factory to create a Replica3 instance, given a user-defined identifier
    /// \details Identifier is returned by Replica3::WriteAllocationID() for what type of class to create.<BR>
    /// This is called when you download a replica from another system.<BR>
    /// See Replica3::Dealloc for the corresponding destruction message.<BR>
    /// Return 0 if unable to create the intended object. Note, in that case the other system will still think we have the object and will try to serialize object updates to us. Generally, you should not send objects the other system cannot create.<BR>
    /// \sa Replica3::WriteAllocationID().
    /// Sample implementation:<BR>
    /// {RakNet::RakString typeName; allocationIdBitstream->Read(typeName); if (typeName=="Soldier") return new Soldier; return 0;}<BR>
    /// \param[in] allocationIdBitstream user-defined bitstream uniquely identifying a game object type
    /// \param[in] replicaManager3 Instance of ReplicaManager3 that controls this connection
    /// \return The new replica instance
    public abstract Replica3 AllocReplica(BitStream allocationIdBitstream, ReplicaManager3 replicaManager3);

    /// \brief Get list of all replicas that are constructed for this connection
    /// \param[out] objectsTheyDoHave Destination list. Returned in sorted ascending order, sorted on the value of the Replica3 pointer.
    public native void GetConstructedReplicas(List<Replica3> objectsTheyDoHave);

    /// Returns true if we think this remote connection has this replica constructed
    /// \param[in] replica3 Which replica we are querying
    /// \return True if constructed, false othewise
    public native boolean HasReplicaConstructed(Replica3 replica);

    /// When a new connection connects, before sending any objects, SerializeOnDownloadStarted() is called
    /// \param[out] bitStream Passed to DeserializeOnDownloadStarted()
    public void SerializeOnDownloadStarted(BitStream bitStream) {}

    /// Receives whatever was written in SerializeOnDownloadStarted()
    /// \param[in] bitStream Written in SerializeOnDownloadStarted()
    public void DeserializeOnDownloadStarted(BitStream bitStream) {}

    /// When a new connection connects, after constructing and serialization all objects, SerializeOnDownloadComplete() is called
    /// \param[out] bitStream Passed to DeserializeOnDownloadComplete()
    public void SerializeOnDownloadComplete(BitStream bitStream) {}

    /// Receives whatever was written in DeserializeOnDownloadComplete()
    /// \param[in] bitStream Written in SerializeOnDownloadComplete()
    public void DeserializeOnDownloadComplete(BitStream bitStream) {}

    /// \return The system address passed to the constructor of this object
    public native SystemAddress GetSystemAddress();

    /// \return Returns the RakNetGUID passed to the constructor of this object
    public native RakNetGUID GetRakNetGUID();

    /// \return True if ID_REPLICA_MANAGER_DOWNLOAD_COMPLETE arrived for this connection
    public native boolean GetDownloadWasCompleted();

    /// List of enumerations for how to get the list of valid objects for other systems
    enum ConstructionMode
    {
        /// For every object that does not exist on the remote system, call Replica3::QueryConstruction() every tick.
        /// Do not call Replica3::QueryDestruction()
        /// Do not call Connection_RM3::QueryReplicaList()
        QUERY_REPLICA_FOR_CONSTRUCTION,

        /// For every object that does not exist on the remote system, call Replica3::QueryConstruction() every tick. Based on the call, the object may be sent to the other system.
        /// For every object that does exist on the remote system, call Replica3::QueryDestruction() every tick. Based on the call, the object may be deleted on the other system.
        /// Do not call Connection_RM3::QueryReplicaList()
        QUERY_REPLICA_FOR_CONSTRUCTION_AND_DESTRUCTION,

        /// Do not call Replica3::QueryConstruction() or Replica3::QueryDestruction()
        /// Call Connection_RM3::QueryReplicaList() to determine which objects exist on remote systems
        /// This can be faster than QUERY_REPLICA_FOR_CONSTRUCTION and QUERY_REPLICA_FOR_CONSTRUCTION_AND_DESTRUCTION for large worlds
        /// See GridSectorizer.h under /Source for code that can help with this
        QUERY_CONNECTION_FOR_REPLICA_LIST
    }

    /// \brief Return whether or not downloads to our system should all be processed the same tick (call to RakPeer::Receive() )
    /// \details Normally the system will send ID_REPLICA_MANAGER_DOWNLOAD_STARTED, ID_REPLICA_MANAGER_CONSTRUCTION for all downloaded objects,
    /// ID_REPLICA_MANAGER_SERIALIZE for each downloaded object, and lastly ID_REPLICA_MANAGER_DOWNLOAD_COMPLETE.
    /// This enables the application to show a downloading splash screen on ID_REPLICA_MANAGER_DOWNLOAD_STARTED, a progress bar, and to close the splash screen and activate all objects on ID_REPLICA_MANAGER_DOWNLOAD_COMPLETE
    /// However, if the application was not set up for this then it would result in incomplete objects spread out over time, and cause problems
    /// If you return true from QueryGroupDownloadMessages(), then these messages will be returned all in one tick, returned only when the download is complete
    /// \note ID_REPLICA_MANAGER_DOWNLOAD_STARTED calls the callback DeserializeOnDownloadStarted()
    /// \note ID_REPLICA_MANAGER_DOWNLOAD_COMPLETE calls the callback DeserializeOnDownloadComplete()
    public boolean QueryGroupDownloadMessages() { return false; }

    /// \brief Queries how to get the list of objects that exist on remote systems
    /// \details The default of calling QueryConstruction for every known object is easy to use, but not efficient, especially for large worlds where many objects are outside of the player's circle of influence.<BR>
    /// QueryDestruction is also not necessarily useful or efficient, as object destruction tends to happen in known cases, and can be accomplished by calling Replica3::BroadcastDestruction()
    /// QueryConstructionMode() allows you to specify more efficient algorithms than the default when overriden.
    /// \return How to get the list of objects that exist on the remote system. You should always return the same value for a given connection
    public ConstructionMode QueryConstructionMode() { return ConstructionMode.QUERY_REPLICA_FOR_CONSTRUCTION_AND_DESTRUCTION; }

    /// \brief Callback used when QueryConstructionMode() returns QUERY_CONNECTION_FOR_REPLICA_LIST
    /// \details This advantage of this callback is if that there are many objects that a particular connection does not have, then we do not have to iterate through those
    /// objects calling QueryConstruction() for each of them.<BR>
    ///<BR>
    /// See GridSectorizer in the Source directory as a method to find all objects within a certain radius in a fast way.<BR>
    ///<BR>
    /// \param[out] newReplicasToCreate Anything in this list will be created on the remote system
    /// \param[out] existingReplicasToDestroy Anything in this list will be destroyed on the remote system
    public void QueryReplicaList(
        List<Replica3> newReplicasToCreate,
        List<Replica3> existingReplicasToDestroy) {}

    /// \brief Override which replicas to serialize and in what order for a connection for a ReplicaManager3::Update() cycle
    /// \details By default, Connection_RM3 will iterate through queryToSerializeReplicaList and call QuerySerialization() on each Replica in that list
    /// queryToSerializeReplicaList is populated in the order in which ReplicaManager3::Reference() is called for those objects.
    /// If you write to to \a replicasToSerialize and return true, you can control in what order and for which replicas to call QuerySerialization()
    /// Example use case:
    /// We have more data to send then the bandwidth supports, so want to prioritize sends. For example enemies shooting are more important than animation effects
    /// When QuerySerializationList(), sort objects by priority, and write the list to \a replicasToSerialize, optionally skipping objects with a lower serialization frequency
    /// If you hit your bandwidth limit when checking SerializeParameters::bitsWrittenSoFar, you can return RM3SR_DO_NOT_SERIALIZE for all remaining items
    /// \note Only replicas written to replicasToSerialize are transmitted. Even if you returned RM3SR_SERIALIZED_ALWAYS a prior ReplicaManager3::Update() cycle, the replica will not be transmitted if it is not in replicasToSerialize
    /// \note If you do not know what objects are candidates for serialization, you can use queryToSerializeReplicaList as a source for your filtering or sorting operations
    /// \param[in] replicasToSerialize List of replicas to call QuerySerialization() on
    /// \return Return true to use replicasToSerialize (replicasToSerialize may be empty if desired). Otherwise return false.
    public boolean QuerySerializationList(List<Replica3> replicasToSerialize) { return false; }

}
