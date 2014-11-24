#include "com_spireofbabel_raknet4j_Packet.h"

#include <RakNetTypes.h>

#include "handle.h"
#include "type_builders.h"

using namespace RakNet;

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    nativePacket
 * Signature: ()Lcom/spireofbabel/raknet4j/NativeHandle;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_Packet_nativePacket
(JNIEnv *env, jobject object)
{
    Packet *instance = new Packet();

    return createHandleObject<Packet>(env, instance);
}

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    getSystemAddress
 * Signature: ()Lcom/spireofbabel/raknet4j/RakNetSocket2/SystemAddress;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_Packet_getSystemAddress
(JNIEnv *env, jobject object)
{
    Packet *instance = getHandle<Packet>(env, object);

    return buildSystemAddress(env, createHandleObject<SystemAddress>(env, &instance->systemAddress));
}

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    setSystemAddress
 * Signature: (Lcom/spireofbabel/raknet4j/RakNetSocket2/SystemAddress;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_Packet_setSystemAddress
(JNIEnv *env, jobject object, jobject systemAddress)
{
    Packet *instance = getHandle<Packet>(env, object);
    SystemAddress *address = getHandle<SystemAddress>(env, object);

    instance->systemAddress = *address;
}

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    getGuid
 * Signature: ()Lcom/spireofbabel/raknet4j/RakNetGUID;
 */
JNIEXPORT jobject JNICALL Java_com_spireofbabel_raknet4j_Packet_getGuid
(JNIEnv *env, jobject object)
{
    Packet *instance = getHandle<Packet>(env, object);

    return buildRakNetGUID(env, createHandleObject<RakNetGUID>(env, &instance->guid));
}

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    setGuid
 * Signature: (Lcom/spireofbabel/raknet4j/RakNetGUID;)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_Packet_setGuid
(JNIEnv *env, jobject object, jobject guid)
{
    Packet *instance = getHandle<Packet>(env, object);
    RakNetGUID *guidInstance = getHandle<RakNetGUID>(env, object);

    instance->guid = *guidInstance;
}

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    getLength
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_spireofbabel_raknet4j_Packet_getLength
(JNIEnv *env, jobject object)
{
    Packet *instance = getHandle<Packet>(env, object);

    return (jlong)instance->length;
}

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    setLength
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_Packet_setLength
(JNIEnv *env, jobject object, jlong length)
{
    Packet *instance = getHandle<Packet>(env, object);
    instance->length = (unsigned int)length;
}

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    getBitSize
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_spireofbabel_raknet4j_Packet_getBitSize
(JNIEnv *env, jobject object)
{
    Packet *instance = getHandle<Packet>(env, object);

    return (jlong)instance->bitSize;
}

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    setBitSize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_Packet_setBitSize
(JNIEnv *env, jobject object, jlong bitSize)
{
    Packet *instance = getHandle<Packet>(env, object);

    instance->bitSize = (BitSize_t)bitSize;
}

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    getData
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_spireofbabel_raknet4j_Packet_getData
(JNIEnv *env, jobject object)
{
    Packet *instance = getHandle<Packet>(env, object);

    jbyteArray result = env->NewByteArray(instance->length);

    env->SetByteArrayRegion(result, 0, instance->length, reinterpret_cast<jbyte *>(instance->data));

    return result;
}

/*
 * Class:     com_spireofbabel_raknet4j_Packet
 * Method:    setData
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_com_spireofbabel_raknet4j_Packet_setData
(JNIEnv *env, jobject object, jbyteArray data)
{
    Packet *instance = getHandle<Packet>(env, object);

    // Clear the old data.
    delete [] instance->data;

    // Set the new data.
    int dataLen = env->GetArrayLength(data);
    instance->data = new unsigned char[dataLen];
    env->GetByteArrayRegion(data, 0, dataLen, reinterpret_cast<jbyte*>(instance->data));
}