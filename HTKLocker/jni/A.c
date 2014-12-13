#include <jni.h>

#include "tool.h"
#include "com_flo_util_HTK.h"

//main_HCopy(argc, argv);
//	main_HInit(argc, argv);
//	main_HRest(argc, argv);
	//main_HParse(argc, argv);

//main_HVite(argc, argv);


JNIEXPORT void JNICALL Java_com_flo_util_HTK_HCopy
(JNIEnv * env, jclass j, jstring jconfigFilePath,jstring jwavlistPath) {
	char* configFilePath = (char*) (*env)->GetStringUTFChars(env,jconfigFilePath, 0);
	char* wavlistPath = (char*)  (*env)->GetStringUTFChars(env,jwavlistPath, 0);

	int argc=5;
	char *argv[7];
	argv[0]="Hcopy";
	argv[1]="-C";
	argv[2]=configFilePath;
	argv[3]="-S";
	argv[4]=wavlistPath;
	main_HCopy(argc, argv);
}

/*
 * Class:     com_flo_util_HTKFunc
 * Method:    HInit
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_flo_util_HTK_HInit
(JNIEnv * env, jclass j, jstring jpathtring) {}
/*
 * Class:     com_flo_util_HTKFunc
 * Method:    HRest
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_flo_util_HTK_HRest
(JNIEnv * env, jclass j, jstring jpathtring) {}

/*
 * Class:     com_flo_util_HTKFunc
 * Method:    HVite
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_flo_util_HTK_HVite
(JNIEnv * env, jclass j, jstring jpathtring) {}

