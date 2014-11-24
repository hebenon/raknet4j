#include <jni.h>

#include <RakNetTypes.h>
#include <PacketPriority.h>

#ifndef _Included_type_builders
#define _Included_type_builders
#ifdef __cplusplus
extern "C" {
#endif

    jobject buildBitStream(JNIEnv *env, jobject handle);
    jobject buildPacket(JNIEnv *env, jobject handle);
    jobject buildRakNetGUID(JNIEnv *env, jobject handle);
    jobject buildReplicaManager3(JNIEnv *env, jobject handle);
    jobject buildSystemAddress(JNIEnv *env, jobject handle);

#ifdef __cplusplus
}
#endif
#endif
