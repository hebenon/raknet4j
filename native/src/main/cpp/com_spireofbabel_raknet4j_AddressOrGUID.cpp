#include "com_spireofbabel_raknet4j_AddressOrGUID.h"

#include <RakNetTypes.h>

#include "handle.h"

using namespace RakNet;

/*
 * Class:     com_spireofbabel_raknet4j_AddressOrGUID
 * Method:    nativeAddressOrGUID
 * Signature: (Lcom/spireofbabel/raknet4j/AddressOrGUID;)Lcom/spireofbabel/raknet4j/NativeHandle;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_AddressOrGUID_nativeAddressOrGUID__Lcom_spireofbabel_raknet4j_AddressOrGUID_2
(JNIEnv *env, jobject, jobject other)
{
    AddressOrGUID *nativeOther = getHandle<AddressOrGUID>(env, other);

    AddressOrGUID *instance = new AddressOrGUID(*nativeOther);

    return createHandleObject<AddressOrGUID>(env, instance);
}

/*
 * Class:     com_spireofbabel_raknet4j_AddressOrGUID
 * Method:    nativeAddressOrGUID
 * Signature: (Lcom/spireofbabel/raknet4j/RakNetSocket2/SystemAddress;)Lcom/spireofbabel/raknet4j/NativeHandle;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_AddressOrGUID_nativeAddressOrGUID__Lcom_spireofbabel_raknet4j_RakNetSocket2_SystemAddress_2
(JNIEnv *env, jobject, jobject address)
{
    SystemAddress *nativeAddress = getHandle<SystemAddress>(env, address);

    AddressOrGUID *instance = new AddressOrGUID(*nativeAddress);

    return createHandleObject<AddressOrGUID>(env, instance);
}

/*
 * Class:     com_spireofbabel_raknet4j_AddressOrGUID
 * Method:    nativeAddressOrGUID
 * Signature: (Lcom/spireofbabel/raknet4j/RakNetGUID;)Lcom/spireofbabel/raknet4j/NativeHandle;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_AddressOrGUID_nativeAddressOrGUID__Lcom_spireofbabel_raknet4j_RakNetGUID_2
(JNIEnv *env, jobject, jobject guid)
{
    RakNetGUID *nativeGUID = getHandle<RakNetGUID>(env, guid);

    AddressOrGUID *instance = new AddressOrGUID(*nativeGUID);

    return createHandleObject<AddressOrGUID>(env, instance);
}