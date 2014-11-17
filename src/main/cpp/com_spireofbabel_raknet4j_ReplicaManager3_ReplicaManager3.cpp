#include "com_spireofbabel_raknet4j_ReplicaManager3_ReplicaManager3.h"

#include <ReplicaManager3.h>

#include "handle.h"
#include "enum_conversions.h"
#include "type_builders.h"

using namespace RakNet;

// ReplicaManager3Proxy acts as a bridge between the abstract C++ ReplicaManager3 and
// the Java implementations.
class ReplicaManager3Proxy : public ReplicaManager3
{
public:
    ReplicaManager3Proxy(JNIEnv *_env, jobject _javaSideObject)
    {
        env = _env;
        javaSideObject = _javaSideObject;
        
        jclass clazz = env->GetObjectClass(javaSideObject);
        
        // Precache the proxy methods.
        javaAllocConnectionID = env->GetMethodID(clazz, "AllocConnection","(Lcom/spireofbabel/raknet4j/RakNetSocket2/SystemAddress;Lcom/spireofbabel/raknet4j/RakNetGUID;)Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;");        
        javaDeallocConnectionID = env->GetMethodID(clazz, "DeallocConnection","(Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;)V");
    }
    
    virtual Connection_RM3* AllocConnection(const SystemAddress &systemAddress, RakNetGUID rakNetGUID) const {
        
        // Marshal the java arguments.
        jobject javaSystemAddress = buildSystemAddress(env,createHandleObject<SystemAddress>(env,&systemAddress));
        jobject javaRakNetGUID = buildRakNetGUID(env, createHandleObject<RakNetGUID>(env, &rakNetGUID));
        
        // Call the Java method.
        jobject result = env->CallObjectMethod(javaSideObject, javaAllocConnectionID, javaSystemAddress, javaRakNetGUID);
        
        // Marshall the response back.
        return getHandle<Connection_RM3>(env,result);
    }
    
    virtual void DeallocConnection(Connection_RM3 *connection) const {
        env->CallObjectMethod(javaSideObject, javaDeallocConnectionID, createHandleObject(env, connection));
    }
    
private:
    JNIEnv *env;
    jobject javaSideObject;
    jmethodID javaAllocConnectionID;
    jmethodID javaDeallocConnectionID;
    
};

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_ReplicaManager3
 * Method:    nativeReplicaManager3
 * Signature: ()J
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_ReplicaManager3_nativeReplicaManager3
(JNIEnv *env, jobject object)
{
    ReplicaManager3 *instance = new ReplicaManager3Proxy(env, object);
    
    return createHandleObject(env,instance);
}