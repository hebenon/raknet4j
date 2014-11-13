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

/*
 * Class:     com_spireofbabel_raknet4j_RakPeerInterface
 * Method:    SetMaximumIncomingConnections
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_SetMaximumIncomingConnections
  (JNIEnv *env, jobject object, jint maximumConnections)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    instance->SetMaximumIncomingConnections(reinterpret_cast<int>(maximumConnections));
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
    return reinterpret_cast<jint>((int)instance->GetMaximumIncomingConnections());
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

    return reinterpret_cast<jint>((int)instance->NumberOfConnections());
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
    unsigned short nativeRemotePort = (unsigned short)reinterpret_cast<int>(remotePort);
    unsigned nativeConnectionSocketIndex = (unsigned)connectionSocketIndex;
    unsigned nativeSendConnectionAttemptCount = (unsigned)nativeSendConnectionAttemptCount;
    unsigned nativeTimeBetweenSendConnectionAttemptsMS = (unsigned)timeBetweenSendConnectionAttemptsMS;
    RakNet::TimeMS nativeTimeoutTime = (RakNet::TimeMS)reinterpret_cast<int>(timeoutTime);
    
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

    instance->Shutdown(reinterpret_cast<int>(blockDuration), (unsigned char)reinterpret_cast<int>(orderingChannel), convertPacketPriority(env, disconnectionNotificationPriority));
}

JNIEXPORT jboolean JNICALL Java_com_spireofbabel_raknet4j_RakPeerInterface_IsActive
  (JNIEnv *env, jobject object)
{
    RakPeerInterface *instance = getHandle<RakPeerInterface>(env, object);

    return (jboolean)instance->IsActive();
}