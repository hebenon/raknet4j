#include "com_spireofbabel_raknet4j_RakPeerInterface.h"

#include <cstring>

#include <RakPeerInterface.h>

#include "handle.h"
#include "enum_conversions.h"

using namespace RakNet;

JNIEXPORT jlong JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_nativeGetInstance
  (JNIEnv *, jclass)
{
    RakPeerInterface *instance = RakPeerInterface :: GetInstance();

    return reinterpret_cast<jlong>(instance);
}

JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_nativeDestroyInstance
  (JNIEnv *, jclass, jlong handle)
{
    RakPeerInterface *instance = reinterpret_cast<RakPeerInterface *>(handle);

    RakPeerInterface :: DestroyInstance(instance);
}

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    Startup
 * Signature: (I[Lcom/spireofbabel/raknet4j/SocketDescriptor;II)Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_Startup
  (JNIEnv *env, jobject object, jint maxConnections, jobjectArray socketDescriptors, jint maxThreadPriority)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    jsize descriptorCount = env->GetArrayLength(socketDescriptors);
    SocketDescriptor *descriptors = new SocketDescriptor[descriptorCount];

    for(int i = 0; i < descriptorCount; i++)
    {
        jobject element = env->GetObjectArrayElement(socketDescriptors, i);

        SocketDescriptor *descriptor = getHandle<SocketDescriptor>(env, element);
        memcpy(&descriptors[i], descriptor, sizeof(SocketDescriptor));

        env->DeleteLocalRef(element);
    }

    StartupResult result = instance->Startup(reinterpret_cast<int>(maxConnections), descriptors, reinterpret_cast<int>(descriptorCount), reinterpret_cast<int>(maxThreadPriority));

    delete [] descriptors;

    return convertStartupResult(env, result);
}

JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_Shutdown
(JNIEnv *env, jobject object, jint blockDuration, jint orderingChannel, jobject disconnectionNotificationPriority)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    instance->Shutdown(reinterpret_cast<int>(blockDuration), (unsigned char)reinterpret_cast<int>(orderingChannel), convertPacketPriority(env, disconnectionNotificationPriority));
}

JNIEXPORT jboolean JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_IsActive
  (JNIEnv *env, jobject object)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    return (jboolean)instance->IsActive();
}