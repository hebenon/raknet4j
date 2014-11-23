#include "com_spireofbabel_raknet4j_RakNetSocket2_SystemAddress.h"
#include <RakNetTypes.h>

#include "handle.h"

using namespace RakNet;

/*
 * Class:     com_spireofbabel_raknet4j_RakNetSocket2_SystemAddress
 * Method:    nativeSystemAddress
 * Signature: ()Lcom/spireofbabel/raknet4j/NativeHandle;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_RakNetSocket2_SystemAddress_nativeSystemAddress__
(JNIEnv *env, jobject object)
{
    SystemAddress *addressObject = new SystemAddress();

    return createHandleObject<SystemAddress>(env, addressObject);
}

/*
 * Class:     com_spireofbabel_raknet4j_RakNetSocket2_SystemAddress
 * Method:    nativeSystemAddress
 * Signature: (Ljava/lang/String;)Lcom/spireofbabel/raknet4j/NativeHandle;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_RakNetSocket2_SystemAddress_nativeSystemAddress__Ljava_lang_String_2
(JNIEnv *env, jobject object, jstring address)
{
    const char *nativeAddress = env->GetStringUTFChars(address, 0);
    SystemAddress *addressObject = new SystemAddress(nativeAddress);
    env->ReleaseStringUTFChars(address, nativeAddress);

    return createHandleObject<SystemAddress>(env, addressObject);
}

/*
 * Class:     com_spireofbabel_raknet4j_RakNetSocket2_SystemAddress
 * Method:    nativeSystemAddress
 * Signature: (Ljava/lang/String;I)Lcom/spireofbabel/raknet4j/NativeHandle;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_RakNetSocket2_SystemAddress_nativeSystemAddress__Ljava_lang_String_2I
(JNIEnv *env, jobject object, jstring address, jint port)
{
    const char *nativeAddress = env->GetStringUTFChars(address, 0);
    SystemAddress *addressObject = new SystemAddress(nativeAddress, (unsigned short)port);
    env->ReleaseStringUTFChars(address, nativeAddress);

    return createHandleObject<SystemAddress>(env, addressObject);
}

/*
 * Class:     com_spireofbabel_raknet4j_RakNetSocket2_SystemAddress
 * Method:    nativeDestroyInstance
 * Signature: (Lcom/spireofbabel/raknet4j/NativeHandle;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_RakNetSocket2_SystemAddress_nativeDestroyInstance
(JNIEnv *env, jclass, jobject instance)
{
    SystemAddress *address = getHandle<SystemAddress>(env, instance);

    delete address;
}