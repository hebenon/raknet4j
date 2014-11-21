#include "com_spireofbabel_raknet4j_ConsoleServer.h"
#include <ConsoleServer.h>

#include "handle.h"

using namespace RakNet;

/*JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_ConsoleServer_nativeGetInstance
  (JNIEnv *env, jclass)
{
    ConsoleServer *instance = ConsoleServer :: GetInstance();

    return createHandleObject<ConsoleServer>(env, instance);
}

JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_ConsoleServer_nativeDestroyInstance
  (JNIEnv *env, jclass, jobject handle)
{
    ConsoleServer *instance = getHandle<ConsoleServer>(env, handle);

    ConsoleServer :: DestroyInstance(instance);
}*/