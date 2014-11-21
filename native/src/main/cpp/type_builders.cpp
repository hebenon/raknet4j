#include "type_builders.h"

jobject buildBitStream(JNIEnv *env, jobject handle)
{
    jclass bitStreamClass = env->FindClass("Lcom/spireofbabel/raknet4j/BitStream;");
    jmethodID constructor = env->GetMethodID(bitStreamClass, "<init>","(Lcom/spireofbabel/raknet4j/NativeHandle;)V");

    return env->NewObject(bitStreamClass, constructor, handle);
}

jobject buildReplicaManager3(JNIEnv *env, jobject handle)
{
    jclass replicaManager3Class = env->FindClass("Lcom/spireofbabel/raknet4j/ReplicaManager3/ReplicaManager3;");
    jmethodID constructor = env->GetMethodID(replicaManager3Class, "<init>","(Lcom/spireofbabel/raknet4j/NativeHandle;)V");

    return env->NewObject(replicaManager3Class, constructor, handle);
}

jobject buildSystemAddress(JNIEnv *env, jobject handle)
{
    jclass systemAddressClass = env->FindClass("Lcom/spireofbabel/raknet4j/RakNetSocket2/SystemAddress;");
    jmethodID constructor = env->GetMethodID(systemAddressClass, "<init>","(Lcom/spireofbabel/raknet4j/NativeHandle;)V");
    
    return env->NewObject(systemAddressClass, constructor, handle);
}

jobject buildRakNetGUID(JNIEnv *env, jobject handle)
{
    jclass rakNetGUIDClass = env->FindClass("Lcom/spireofbabel/raknet4j/RakNetSocket2/RakNetGUID;");
    jmethodID constructor = env->GetMethodID(rakNetGUIDClass, "<init>","(Lcom/spireofbabel/raknet4j/NativeHandle;)V");
    
    return env->NewObject(rakNetGUIDClass, constructor, handle);
}