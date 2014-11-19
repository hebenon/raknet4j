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
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_nativeSocketDescriptor
  (JNIEnv *env, jclass clazz)
  {
    SocketDescriptor *instance = new SocketDescriptor();

    return createHandleObject(env,instance);
  }

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    nativeDestroyInstance
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_nativeDestroyInstance
  (JNIEnv *env, jclass clazz, jobject handle)
{
  SocketDescriptor *instance = dereferenceHandle<SocketDescriptor>(env, handle);
  delete instance;
}

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    getPort
 * Signature: ()S
 */
JNIEXPORT jint JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_getPort
  (JNIEnv *env, jobject object)
  {
    SocketDescriptor *instance = getHandle<SocketDescriptor>(env,object);

    return (jint)instance->port;
  }

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    setPort
 * Signature: (S)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_setPort
  (JNIEnv *env, jobject object, jint port)
  {
    SocketDescriptor *instance = getHandle<SocketDescriptor>(env,object);

    instance->port = (unsigned short)port;
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