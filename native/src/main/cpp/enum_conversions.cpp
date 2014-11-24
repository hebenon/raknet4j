#include "enum_conversions.h"

using namespace RakNet;

jobject convertStartupResult(JNIEnv *env, StartupResult result)
{
    jclass enumClass = env->FindClass("com/spireofbabel/raknet4j/RakNetEnums/StartupResult");
    switch(result) {
        case RAKNET_STARTED:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "RAKNET_STARTED", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case RAKNET_ALREADY_STARTED:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "RAKNET_ALREADY_STARTED", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case INVALID_SOCKET_DESCRIPTORS:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "INVALID_SOCKET_DESCRIPTORS", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case INVALID_MAX_CONNECTIONS:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "INVALID_MAX_CONNECTIONS", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case SOCKET_FAMILY_NOT_SUPPORTED:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "SOCKET_FAMILY_NOT_SUPPORTED", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case SOCKET_PORT_ALREADY_IN_USE:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "SOCKET_PORT_ALREADY_IN_USE", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case SOCKET_FAILED_TO_BIND:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "SOCKET_FAILED_TO_BIND", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case SOCKET_FAILED_TEST_SEND:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "SOCKET_FAILED_TEST_SEND", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case PORT_CANNOT_BE_ZERO:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "PORT_CANNOT_BE_ZERO", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case FAILED_TO_CREATE_NETWORK_THREAD:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "FAILED_TO_CREATE_NETWORK_THREAD", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case COULD_NOT_GENERATE_GUID:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "COULD_NOT_GENERATE_GUID", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case STARTUP_OTHER_FAILURE:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "STARTUP_OTHER_FAILURE", "Lcom/spireofbabel/raknet4j/RakNetEnums/StartupResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
    }

    return 0;
}

jobject convertConnectionAttemptResult(JNIEnv *env, ConnectionAttemptResult result)
{
    jclass enumClass = env->FindClass("com/spireofbabel/raknet4j/RakNetEnums/ConnectionAttemptResult");
    switch(result) {
        case CONNECTION_ATTEMPT_STARTED:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "CONNECTION_ATTEMPT_STARTED", "Lcom/spireofbabel/raknet4j/RakNetEnums/ConnectionAttemptResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case INVALID_PARAMETER:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "INVALID_PARAMETER", "Lcom/spireofbabel/raknet4j/RakNetEnums/ConnectionAttemptResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case CANNOT_RESOLVE_DOMAIN_NAME:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "CANNOT_RESOLVE_DOMAIN_NAME", "Lcom/spireofbabel/raknet4j/RakNetEnums/ConnectionAttemptResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case ALREADY_CONNECTED_TO_ENDPOINT:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "ALREADY_CONNECTED_TO_ENDPOINT", "Lcom/spireofbabel/raknet4j/RakNetEnums/ConnectionAttemptResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case CONNECTION_ATTEMPT_ALREADY_IN_PROGRESS:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "CONNECTION_ATTEMPT_ALREADY_IN_PROGRESS", "Lcom/spireofbabel/raknet4j/RakNetEnums/ConnectionAttemptResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
        case SECURITY_INITIALIZATION_FAILED:
        {
            jfieldID enumField = env->GetStaticFieldID(enumClass, "SECURITY_INITIALIZATION_FAILED", "Lcom/spireofbabel/raknet4j/RakNetEnums/ConnectionAttemptResult;");
            return env->GetStaticObjectField(enumClass, enumField);
        }
    }
}

PacketPriority convertPacketPriority(JNIEnv *env, jobject enumObj)
{
    jclass enumClass = env->FindClass("com/spireofbabel/raknet4j/RakNetEnums/PacketPriority");
    jmethodID getOrdinalMethod = env->GetMethodID(enumClass, "ordinal", "()I");
    jint value = env->CallIntMethod(enumObj, getOrdinalMethod);

    return (PacketPriority)value;
}

PacketReliability convertPacketReliability(JNIEnv *env, jobject enumObj)
{
    jclass enumClass = env->FindClass("com/spireofbabel/raknet4j/RakNetEnums/PacketReliability");
    jmethodID getOrdinalMethod = env->GetMethodID(enumClass, "ordinal", "()I");
    jint value = env->CallIntMethod(enumObj, getOrdinalMethod);

    return (PacketReliability)value;
}