#include <jni.h>

#include "tool.h"
#include "com_flo_util_HTKFunc.h"

int mainaa(int argc, char *argv[]){
//main_HCopy(argc, argv);
	main_HInit(argc, argv);
//	smain_HRest(argc, argv);
 //main_HParse(argc, argv);


//main_HVite(argc, argv);
}


JNIEXPORT void JNICALL Java_com_flo_util_HTKFunc_HCopy
  (JNIEnv * env, jclass j, jstring jpathtring){}

/*
 * Class:     com_flo_util_HTKFunc
 * Method:    HInit
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_flo_util_HTKFunc_HInit
(JNIEnv * env, jclass j, jstring jpathtring){}
/*
 * Class:     com_flo_util_HTKFunc
 * Method:    HRest
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_flo_util_HTKFunc_HRest
(JNIEnv * env, jclass j, jstring jpathtring){}

/*
 * Class:     com_flo_util_HTKFunc
 * Method:    HVite
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_flo_util_HTKFunc_HVite
(JNIEnv * env, jclass j, jstring jpathtring){}

