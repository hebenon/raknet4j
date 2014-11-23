#include "com_spireofbabel_raknet4j_RakPeerInterface.h"

#include <cstring>

#include <RakPeerInterface.h>

#include "handle.h"
#include "enum_conversions.h"
#include "type_builders.h"

using namespace RakNet;

JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_nativeGetInstance
  (JNIEnv *env, jclass clazz)
{
    RakPeerInterface *instance = RakPeerInterface :: GetInstance();

    return createHandleObject(env, instance);
}

JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_nativeDestroyInstance
  (JNIEnv *env, jclass clazz, jobject handle)
{
    RakPeerInterface *instance = dereferenceHandle<RakPeerInterface>(env, handle);

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

    StartupResult result = instance->Startup((int)maxConnections, descriptors, (int)descriptorCount, (int)maxThreadPriority);

    delete [] descriptors;

    return convertStartupResult(env, result);
}

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    SetMaximumIncomingConnections
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_SetMaximumIncomingConnections
  (JNIEnv *env, jobject object, jint maximumConnections)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    instance->SetMaximumIncomingConnections((int)maximumConnections);
}

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    GetMaximumIncomingConnections
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_GetMaximumIncomingConnections
  (JNIEnv *env, jobject object)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);
    
    // There's some confusion in the set method signatures, so I've constrained the result to int.
    return (jint)instance->GetMaximumIncomingConnections();
}

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    NumberOfConnections
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_NumberOfConnections
  (JNIEnv *env, jobject object)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    return (jint)instance->NumberOfConnections();
}

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    Connect
 * Signature: (Ljava/lang/String;I[BLcom/spireofbabel/raknet4j/PublicKey;IIII)Lcom/spireofbabel/raknet4j/RakNetEnums/ConnectionAttemptResult;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_Connect
  (JNIEnv *env, jobject object, jstring hostAddress, jint remotePort, jbyteArray passwordData, jobject publicKey, jint connectionSocketIndex, jint sendConnectionAttemptCount, jint timeBetweenSendConnectionAttemptsMS, jint timeoutTime)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    // Marshall the arguments.
    const char *nativeHostAddress = env->GetStringUTFChars(hostAddress, 0);
    unsigned short nativeRemotePort = (unsigned short)remotePort;
    unsigned nativeConnectionSocketIndex = (unsigned)connectionSocketIndex;
    unsigned nativeSendConnectionAttemptCount = (unsigned)nativeSendConnectionAttemptCount;
    unsigned nativeTimeBetweenSendConnectionAttemptsMS = (unsigned)timeBetweenSendConnectionAttemptsMS;
    RakNet::TimeMS nativeTimeoutTime = (RakNet::TimeMS)timeoutTime;
    
    PublicKey *nativePublicKey = 0;
    if(0 != publicKey)
        getHandle<RakPeerInterface>(env, object);
    
    int passwordDataLen = env->GetArrayLength (passwordData);
    char* nativePasswordData = new char[passwordDataLen];
    env->GetByteArrayRegion(passwordData, 0, passwordDataLen, reinterpret_cast<jbyte*>(nativePasswordData));

    ConnectionAttemptResult result = instance->Connect(nativeHostAddress,
        nativeRemotePort,
        nativePasswordData,
        passwordDataLen,
        nativePublicKey,
        nativeConnectionSocketIndex,
        nativeTimeBetweenSendConnectionAttemptsMS,
        nativeTimeoutTime);

    env->ReleaseStringUTFChars(hostAddress, nativeHostAddress);
    delete [] nativePasswordData;

    return convertConnectionAttemptResult(env,result);
}

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    ConnectWithSocket
 * Signature: (Ljava/lang/String;I[BLcom/spireofbabel/raknet4j/RakNetSocket2/RakNetSocket2;Lcom/spireofbabel/raknet4j/PublicKey;III)Lcom/spireofbabel/raknet4j/RakNetEnums/ConnectionAttemptResult;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_ConnectWithSocket
  (JNIEnv *env, jobject object, jstring hostAddress, jint remotePort, jbyteArray passwordData, jobject socket, jobject publicKey, jint sendConnectionAttemptCount, jint timeBetweenSendConnectionAttemptsMS, jint timeoutTime)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    //return instance->ConnectWithSocket();
}

JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_Shutdown
(JNIEnv *env, jobject object, jint blockDuration, jint orderingChannel, jobject disconnectionNotificationPriority)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    instance->Shutdown((int)blockDuration, (unsigned char)orderingChannel, convertPacketPriority(env, disconnectionNotificationPriority));
}

JNIEXPORT jboolean JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_IsActive
  (JNIEnv *env, jobject object)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    return (jboolean)instance->IsActive();
}

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    Receive
 * Signature: ()Lcom/spireofbabel/raknet4j/Packet;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_Receive
(JNIEnv *env, jobject object)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    Packet *packet = instance->Receive();

    // Result could be null, or valid
    if(!packet)
    {
        return 0;
    } else {
        return buildPacket(env, createHandleObject<Packet>(env, packet));
    }
}

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    DeallocatePacket
 * Signature: (Lcom/spireofbabel/raknet4j/Packet;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_DeallocatePacket
(JNIEnv *env, jobject object, jobject packet)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);
    Packet *nativePacket = getHandle<Packet>(env, packet);

    instance->DeallocatePacket(nativePacket);
}

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    AttachPlugin
 * Signature: (Lcom/spireofbabel/raknet4j/PluginInterface2;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_AttachPlugin
(JNIEnv *env, jobject object, jobject pluginObject)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);
    
    PluginInterface2 *plugin = getHandle<PluginInterface2>(env, pluginObject);
    
    instance->AttachPlugin(plugin);
}

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    DetachPlugin
 * Signature: (Lcom/spireofbabel/raknet4j/PluginInterface2;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_DetachPlugin
(JNIEnv *env, jobject object, jobject pluginObject)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    PluginInterface2 *plugin = getHandle<PluginInterface2>(env, pluginObject);

    instance->DetachPlugin(plugin);
}