#ifndef _HANDLE_H_INCLUDED_
#define _HANDLE_H_INCLUDED_

inline jfieldID getHandleObjectField(JNIEnv *env, jobject obj)
{
    jclass c = env->GetObjectClass(obj);
    // J is the type signature for long:
    return env->GetFieldID(c, "nativeHandle", "Lcom/spireofbabel/raknet4j/NativeHandle;");
}

inline jfieldID getHandleField(JNIEnv *env, jobject obj)
{
    jclass c = env->GetObjectClass(obj);
    // J is the type signature for long:
    return env->GetFieldID(c, "nativeHandle", "J");
}

template<typename T>
inline T * dereferenceHandle(JNIEnv *env, jobject handleObject)
{
    jlong handle = env->GetLongField(handleObject, getHandleField(env, handleObject));
    return reinterpret_cast<T *>(handle);
}

template <typename T>
inline T * getHandle(JNIEnv *env, jobject obj)
{
    jobject handleObject = env->GetObjectField(obj, getHandleObjectField(env,obj));
    return dereferenceHandle<T>(env, handleObject);
}

template <typename T>
inline jobject createHandleObject(JNIEnv *env, const T *t)
{
    jlong handle = reinterpret_cast<jlong>(t);
    
    jclass clazz = env->FindClass("com/spireofbabel/raknet4j/NativeHandle");
    jmethodID constructor = env->GetMethodID(clazz, "<init>","(J)V");
    
    jobject handleObject = env->NewObject(clazz, constructor, handle);
    
    return handleObject;
}

template <typename T>
inline void setHandle(JNIEnv *env, jobject obj, T *t)
{
    jobject handleObject = createHandleObject(env, t);
    
    env->SetObjectField(obj, getHandleObjectField(env, obj), handleObject);
}

#endif