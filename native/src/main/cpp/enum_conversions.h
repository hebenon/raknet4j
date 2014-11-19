#include <jni.h>

#include <RakNetTypes.h>
#include <PacketPriority.h>

#ifndef _Included_enum_conversions
#define _Included_enum_conversions
#ifdef __cplusplus
extern "C" {
#endif

jobject convertStartupResult(JNIEnv *env, RakNet::StartupResult result);

jobject convertConnectionAttemptResult(JNIEnv *env, RakNet::ConnectionAttemptResult result);

PacketPriority convertPacketPriority(JNIEnv *env, jobject packetPriorityEnum);

#ifdef __cplusplus
}
#endif
#endif
