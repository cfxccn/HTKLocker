#include <jni.h>


//main_HCopy(argc, argv);
//	main_HInit(argc, argv);
//	main_HRest(argc, argv);
//main_HParse(argc, argv);

//main_HVite(argc, argv);

JNIEXPORT void JNICALL Java_com_flo_util_HCopyFunc_HCopy
(JNIEnv * env, jclass j, jstring jconfigFilePath,jstring jwavlistPath) {
	char* configFilePath = (char*) (*env)->GetStringUTFChars(env,jconfigFilePath, 0);
	char* wavlistPath = (char*) (*env)->GetStringUTFChars(env,jwavlistPath, 0);
	int argc=5;
	char *argv[5];
	argv[0]="HCopy";
	argv[1]="-C";
	argv[2]=configFilePath;
	argv[3]="-S";
	argv[4]=wavlistPath;
	main_HCopy(argc, argv);
}




