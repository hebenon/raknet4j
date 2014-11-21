#include "type_builders.h"

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