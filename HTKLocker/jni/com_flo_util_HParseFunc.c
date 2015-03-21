#include <jni.h>

//main_HCopy(argc, argv);
//	main_HInit(argc, argv);
//	main_HRest(argc, argv);
//main_HParse(argc, argv);

//main_HVite(argc, argv);

JNIEXPORT void JNICALL Java_com_flo_util_HParseFunc_HParse
(JNIEnv * env, jclass j, jstring jgramFilePath, jstring jslfFilePath) {
	char* gramFilePath = (char*) (*env)->GetStringUTFChars(env,jgramFilePath, 0);
	char* slfFilePath = (char*) (*env)->GetStringUTFChars(env,jslfFilePath, 0);
	int argc=3;
	char *argv[3];

	argv[0]="HParse";
	argv[1]=gramFilePath;
	argv[2]=slfFilePath;

	main_HParse(argc, argv);
}

