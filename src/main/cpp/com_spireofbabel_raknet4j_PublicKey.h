/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_spireofbabel_raknet4j_PublicKey */

#ifndef _Included_com_spireofbabel_raknet4j_PublicKey
#define _Included_com_spireofbabel_raknet4j_PublicKey
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_spireofbabel_raknet4j_PublicKey
 * Method:    nativePublicKey
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_spireofbabel_raknet4j_PublicKey_nativePublicKey
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_PublicKey
 * Method:    getPublicKeyMode
 * Signature: ()Lcom/spireofbabel/raknet4j/PublicKey/PublicKeyMode;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_PublicKey_getPublicKeyMode
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_PublicKey
 * Method:    setPublicKeyMode
 * Signature: (Lcom/spireofbabel/raknet4j/PublicKey/PublicKeyMode;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_PublicKey_setPublicKeyMode
  (JNIEnv *, jobject, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_PublicKey
 * Method:    getRemoteServerPublicKey
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_spireofbabel_raknet4j_PublicKey_getRemoteServerPublicKey
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_PublicKey
 * Method:    setRemoteServerPublicKey
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_PublicKey_setRemoteServerPublicKey
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     com_spireofbabel_raknet4j_PublicKey
 * Method:    getMyPublicKey
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_spireofbabel_raknet4j_PublicKey_getMyPublicKey
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_PublicKey
 * Method:    setMyPublicKey
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_PublicKey_setMyPublicKey
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     com_spireofbabel_raknet4j_PublicKey
 * Method:    getMyPrivateKey
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_spireofbabel_raknet4j_PublicKey_getMyPrivateKey
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_PublicKey
 * Method:    setMyPrivateKey
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_PublicKey_setMyPrivateKey
  (JNIEnv *, jobject, jbyteArray);

#ifdef __cplusplus
}
#endif
#endif
