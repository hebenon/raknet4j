#include <jni.h>

#include <RakNetTypes.h>
#include <PacketPriority.h>

#ifndef _Included_type_builders
#define _Included_type_builders
#ifdef __cplusplus
extern "C" {
#endif

    jobject buildSystemAddress(JNIEnv *env, jobject handle);
    jobject buildRakNetGUID(JNIEnv *env, jobject handle);

#ifdef __cplusplus
}
#endif
#endif
