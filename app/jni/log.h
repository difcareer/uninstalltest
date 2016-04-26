#ifndef ETERNITY_LOG_H
#define ETERNITY_LOG_H

#include <jni.h>
#include <android/log.h>

#ifdef NDKLOG

#define TAG        "MainActivity"
#define LOGI(...)    __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGD(...)    __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGW(...)    __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define LOGE(...)    __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

#else

#define LOGI(...)
#define LOGD(...)
#define LOGW(...)
#define LOGE(...)


#endif

#endif //ETERNITY_LOG_H

