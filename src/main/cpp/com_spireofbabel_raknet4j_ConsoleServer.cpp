#include "com_spireofbabel_raknet4j_ConsoleServer.h"
#include <ConsoleServer.h>

using namespace RakNet;

JNIEXPORT jlong JNICALL Java_com_spireofbabel_raknet4j_ConsoleServer_nativeGetInstance
  (JNIEnv *, jclass)
{
    ConsoleServer *instance = ConsoleServer :: GetInstance();

    return reinterpret_cast<jlong>(instance);
}

JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_ConsoleServer_nativeDestroyInstance
  (JNIEnv *, jclass, jlong handle)
{
    ConsoleServer *instance = reinterpret_cast<ConsoleServer *>(handle);

    ConsoleServer :: DestroyInstance(instance);
}