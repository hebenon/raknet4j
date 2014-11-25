LOCAL_PATH := $(call my-dir)

# prepare libRakNet
include $(CLEAR_VARS)
LOCAL_MODULE    := libRakNet
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libRakNet.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)

MY_PREFIX := $(LOCAL_PATH)/../../src/main/cpp/
MY_SOURCES := $(wildcard $(MY_PREFIX)*.cpp)

LOCAL_MODULE    := raknet4jNative-android
LOCAL_SRC_FILES += $(MY_SOURCES:$(MY_PREFIX)%=../../src/main/cpp/%)
LOCAL_C_INCLUDES += $(LOCAL_PATH)/../target/custom-javah
LOCAL_C_INCLUDES += $(LOCAL_PATH)/../../../../RakNet/Source

# Include the static libraries pulled in via Android Maven plugin makefile (see include below)
LOCAL_STATIC_LIBRARIES := $(ANDROID_MAVEN_PLUGIN_LOCAL_STATIC_LIBRARIES)
LOCAL_STATIC_LIBRARIES += RakNet

# Build the shared libary
include $(BUILD_SHARED_LIBRARY)

# Include the Android Maven plugin generated makefile
# Important: Must be the last import in order for Android Maven Plugins paths to work
include $(ANDROID_MAVEN_PLUGIN_MAKEFILE)
