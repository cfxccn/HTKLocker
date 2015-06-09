#include <jni.h>


JNIEXPORT void JNICALL Java_com_flo_util_HViteFunc_HVite
(JNIEnv * env, jclass j, jstring jallMmfFile,jstring jresultFile,jstring jnetSlfFile,jstring jdictFile,jstring jhmmListFile,jstring jmfcFile) {

	char* allMmfFile = (char*) (*env)->GetStringUTFChars(env,jallMmfFile, 0);
	char* resultFile = (char*) (*env)->GetStringUTFChars(env,jresultFile, 0);
	char* netSlfFile = (char*) (*env)->GetStringUTFChars(env,jnetSlfFile, 0);
	char* dictFile = (char*) (*env)->GetStringUTFChars(env,jdictFile, 0);
	char* hmmListFile = (char*) (*env)->GetStringUTFChars(env,jhmmListFile, 0);
	char* mfcFile = (char*) (*env)->GetStringUTFChars(env,jmfcFile, 0);

	int argc=10;
	char *argv[10];


	 argv[0]="HVite";
	 argv[1]="-H";
	 argv[2]=allMmfFile;
	 argv[3]="-i";
	 argv[4]=resultFile;
	 argv[5]="-w";
	 argv[6]=netSlfFile;
	 argv[7]=dictFile;
	 argv[8]=hmmListFile;
	 argv[9]=mfcFile;



	main_HVite(argc, argv);
}
