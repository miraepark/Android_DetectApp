#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_example_samsung_detectapp_NdkActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "C 또는 C++ 구현";
    return env->NewStringUTF(hello.c_str());
}
