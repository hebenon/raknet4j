/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_spireofbabel_raknet4j_SocketDescriptor */

#ifndef _Included_com_spireofbabel_raknet4j_SocketDescriptor
#define _Included_com_spireofbabel_raknet4j_SocketDescriptor
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    nativeSocketDescriptor
 * Signature: ()Lcom/spireofbabel/raknet4j/NativeHandle;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_nativeSocketDescriptor
  (JNIEnv *, jclass);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    nativeDestroyInstance
 * Signature: (Lcom/spireofbabel/raknet4j/NativeHandle;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_nativeDestroyInstance
  (JNIEnv *, jclass, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    getPort
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_getPort
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    setPort
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_setPort
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    getHostAddress
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_getHostAddress
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    setHostAddress
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_setHostAddress
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    getSocketFamily
 * Signature: ()Lcom/spireofbabel/raknet4j/SocketDescriptor/SocketFamily;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_getSocketFamily
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    setSocketFamily
 * Signature: (Lcom/spireofbabel/raknet4j/SocketDescriptor/SocketFamily;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_setSocketFamily
  (JNIEnv *, jobject, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    getBlockingSocket
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_getBlockingSocket
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    setBlockingSocket
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_setBlockingSocket
  (JNIEnv *, jobject, jboolean);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    getExtraSocketOptions
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_getExtraSocketOptions
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_SocketDescriptor
 * Method:    setExtraSocketOptions
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_SocketDescriptor_setExtraSocketOptions
  (JNIEnv *, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
