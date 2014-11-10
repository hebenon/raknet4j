#include "com_spireofbabel_raknet4j_SocketDescriptor.h"
#include <string.h>

#include <RakNetTypes.h>
#include "handle.h"

using namespace RakNet;
/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    nativeGetInstance
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_nativeGetInstance
  (JNIEnv *, jclass)
  {
    SocketDescriptor *instance = new SocketDescriptor();

    return reinterpret_cast<jlong>(instance);
  }

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    nativeDestroyInstance
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_nativeDestroyInstance
  (JNIEnv *, jclass, jlong handle)
  {
    SocketDescriptor *instance = reinterpret_cast<SocketDescriptor *>(handle);
    delete instance;
  }

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    getPort
 * Signature: ()S
 */
JNIEXPORT jchar JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_getPort
  (JNIEnv *env, jobject object)
  {
    SocketDescriptor *instance = getHandle<SocketDescriptor>(env,object);

    return reinterpret_cast<jchar>(instance->port);
  }

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    setPort
 * Signature: (S)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_setPort
  (JNIEnv *env, jobject object, jchar port)
  {
    SocketDescriptor *instance = getHandle<SocketDescriptor>(env,object);

    instance->port = reinterpret_cast<unsigned short>(port);
  }

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    getHostAddress
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_getHostAddress
  (JNIEnv *env, jobject object)
  {
    SocketDescriptor *instance = getHandle<SocketDescriptor>(env,object);

    return env->NewStringUTF(instance->hostAddress);
  }

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    setHostAddress
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_setHostAddress
  (JNIEnv *env, jobject object, jstring hostAddress)
  {
    SocketDescriptor *instance = getHandle<SocketDescriptor>(env,object);

    const char *hostAddressstr = env->GetStringUTFChars(hostAddress, 0);

    strcpy(instance->hostAddress, hostAddressstr);

    env->ReleaseStringUTFChars(hostAddress, hostAddressstr);
  }