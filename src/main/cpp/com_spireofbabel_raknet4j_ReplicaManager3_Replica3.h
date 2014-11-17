/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_spireofbabel_raknet4j_ReplicaManager3_Replica3 */

#ifndef _Included_com_spireofbabel_raknet4j_ReplicaManager3_Replica3
#define _Included_com_spireofbabel_raknet4j_ReplicaManager3_Replica3
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    nativeReplica3
 * Signature: ()Lcom/spireofbabel/raknet4j/NativeHandle;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_nativeReplica3
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    nativeDestroyInstance
 * Signature: (Lcom/spireofbabel/raknet4j/NativeHandle;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_nativeDestroyInstance
  (JNIEnv *, jclass, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    QueryConstruction_ClientConstruction
 * Signature: (Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;Z)Lcom/spireofbabel/raknet4j/ReplicaManager3/Replica3/RM3ConstructionState;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_QueryConstruction_1ClientConstruction
  (JNIEnv *, jobject, jobject, jboolean);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    QueryRemoteConstruction_ClientConstruction
 * Signature: (Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;Z)Z
 */
JNIEXPORT jboolean JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_QueryRemoteConstruction_1ClientConstruction
  (JNIEnv *, jobject, jobject, jboolean);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    QueryConstruction_ServerConstruction
 * Signature: (Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;Z)Lcom/spireofbabel/raknet4j/ReplicaManager3/Replica3/RM3ConstructionState;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_QueryConstruction_1ServerConstruction
  (JNIEnv *, jobject, jobject, jboolean);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    QueryRemoteConstruction_ServerConstruction
 * Signature: (Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;Z)Z
 */
JNIEXPORT jboolean JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_QueryRemoteConstruction_1ServerConstruction
  (JNIEnv *, jobject, jobject, jboolean);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    QueryConstruction_PeerToPeer
 * Signature: (Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;Lcom/spireofbabel/raknet4j/ReplicaManager3/Replica3/Replica3P2PMode;)Lcom/spireofbabel/raknet4j/ReplicaManager3/Replica3/RM3ConstructionState;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_QueryConstruction_1PeerToPeer
  (JNIEnv *, jobject, jobject, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    QueryRemoteConstruction_PeerToPeer
 * Signature: (Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_QueryRemoteConstruction_1PeerToPeer
  (JNIEnv *, jobject, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    QuerySerialization_ClientSerializable
 * Signature: (Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;Z)Lcom/spireofbabel/raknet4j/ReplicaManager3/Replica3/RM3QuerySerializationResult;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_QuerySerialization_1ClientSerializable
  (JNIEnv *, jobject, jobject, jboolean);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    QuerySerialization_ServerSerializable
 * Signature: (Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;Z)Lcom/spireofbabel/raknet4j/ReplicaManager3/Replica3/RM3QuerySerializationResult;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_QuerySerialization_1ServerSerializable
  (JNIEnv *, jobject, jobject, jboolean);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    QuerySerialization_PeerToPeer
 * Signature: (Lcom/spireofbabel/raknet4j/ReplicaManager3/Connection_RM3;Lcom/spireofbabel/raknet4j/ReplicaManager3/Replica3/Replica3P2PMode;)Lcom/spireofbabel/raknet4j/ReplicaManager3/Replica3/RM3QuerySerializationResult;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_QuerySerialization_1PeerToPeer
  (JNIEnv *, jobject, jobject, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    BroadcastDestruction
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_BroadcastDestruction
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    GetCreatingSystemGUID
 * Signature: ()Lcom/spireofbabel/raknet4j/RakNetGUID;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_GetCreatingSystemGUID
  (JNIEnv *, jobject);

/*
 * Class:     com_spireofbabel_raknet4j_ReplicaManager3_Replica3
 * Method:    WasReferenced
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_spireofbabel_raknet4j_ReplicaManager3_Replica3_WasReferenced
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif