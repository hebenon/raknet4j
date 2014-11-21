#include "com_spireofbabel_raknet4j_ReplicaManager3_Connection_RM3.h"

#include <ReplicaManager3.h>

#include "handle.h"
#include "enum_conversions.h"
#include "type_builders.h"

using namespace RakNet;

// Connection_RM3Proxy acts as a bridge between the abstract C++ Connection_RM3 and
// the Java implementations.
class Connection_RM3Proxy : public Connection_RM3
{
public:
    Connection_RM3Proxy(JNIEnv *_env, jobject _javaSideObject, const SystemAddress &_systemAddress, RakNetGUID _guid): Connection_RM3(_systemAddress, _guid)
    {
        env = _env;
        javaSideObject = _javaSideObject;
        
        jclass clazz = env->GetObjectClass(javaSideObject);
        
        // Precache the proxy methods.
        javaAllocReplicaID = env->GetMethodID(clazz, "AllocReplica","(Lcom/spireofbabel/raknet4j/BitStream;Lcom/spireofbabel/raknet4j/ReplicaManager3/ReplicaManager3;)Lcom/spireofbabel/raknet4j/ReplicaManager3/Replica3;");
        
        javaSerializeOnDownloadStartedID = env->GetMethodID(clazz, "SerializeOnDownloadStarted","(Lcom/spireofbabel/raknet4j/BitStream)V");
        javaDeserializeOnDownloadStartedID = env->GetMethodID(clazz, "DeserializeOnDownloadStarted","(Lcom/spireofbabel/raknet4j/BitStream)V");
        javaSerializeOnDownloadCompleteID = env->GetMethodID(clazz, "SerializeOnDownloadCompleted","(Lcom/spireofbabel/raknet4j/BitStream)V");
        javaDeserializeOnDownloadCompleteID = env->GetMethodID(clazz, "DeserializeOnDownloadCompleted","(Lcom/spireofbabel/raknet4j/BitStream)V");
    }
    
    // Proxy methods
    virtual Replica3 *AllocReplica(RakNet::BitStream *allocationId, ReplicaManager3 *replicaManager3)
    {
        // Marshal the java arguments
        jobject javaAllocationId = buildBitStream(env,createHandleObject<BitStream>(env,allocationId));
        jobject javaReplicaManager3 = buildReplicaManager3(env, createHandleObject<ReplicaManager3>(env,replicaManager3));
        
        // Call the Java method
        jobject result = env->CallObjectMethod(javaSideObject, javaAllocReplicaID, javaAllocationId, javaReplicaManager3);
        
        // Marshall the response
        return getHandle<Replica3>(env,result);
    }
    
    virtual void SerializeOnDownloadStarted(RakNet::BitStream *bitStream)
    {
        // Marshal the java arguments
        jobject javaBitStream = buildBitStream(env, createHandleObject<BitStream>(env, bitStream));
        
        // Call the Java method.
        env->CallVoidMethod(javaSideObject, javaSerializeOnDownloadStartedID, javaBitStream);
    }
    
    virtual void DeserializeOnDownloadStarted(RakNet::BitStream *bitStream)
    {
        // Marshal the java arguments
        jobject javaBitStream = buildBitStream(env, createHandleObject<BitStream>(env, bitStream));
        
        // Call the Java method.
        env->CallVoidMethod(javaSideObject, javaDeserializeOnDownloadStartedID, javaBitStream);
    }
    
    virtual void SerializeOnDownloadComplete(RakNet::BitStream *bitStream)
    {
        // Marshal the java arguments
        jobject javaBitStream = buildBitStream(env, createHandleObject<BitStream>(env, bitStream));
        
        // Call the Java method.
        env->CallVoidMethod(javaSideObject, javaSerializeOnDownloadCompleteID, javaBitStream);
    }
    
    virtual void DeserializeOnDownloadComplete(RakNet::BitStream *bitStream)
    {
        // Marshal the java arguments
        jobject javaBitStream = buildBitStream(env, createHandleObject<BitStream>(env, bitStream));
        
        // Call the Java method.
        env->CallVoidMethod(javaSideObject, javaDeserializeOnDownloadCompleteID, javaBitStream);
    }
        
private:
    JNIEnv *env;
    jobject javaSideObject;
    
    // Proxy method IDs. Cached for performance.
    jmethodID javaAllocReplicaID;
    
    jmethodID javaSerializeOnDownloadStartedID;
    jmethodID javaDeserializeOnDownloadStartedID;
    jmethodID javaSerializeOnDownloadCompleteID;
    jmethodID javaDeserializeOnDownloadCompleteID;
    
};