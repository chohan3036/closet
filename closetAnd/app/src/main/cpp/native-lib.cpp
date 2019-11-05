#include <jni.h>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

using namespace cv;

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_closet_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_closet_openCV_1test_detectEdgeJNI(JNIEnv *env, jobject thiz, jlong input_image,
                                                   jlong output_image, jint th1, jint th2) {
    // TODO: implement detectEdgeJNI()
    // 입력 RGBA 이미지를 GRAY 이미지로 변환?
    Mat &inputMat = *(Mat *) input_image;
    Mat &outputMat = *(Mat *) output_image;

    cvtColor(inputMat, outputMat, COLOR_RGB2GRAY);
    Canny(outputMat, outputMat, th1, th2);
}