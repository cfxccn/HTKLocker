#include <jni.h>


JNIEXPORT void JNICALL Java_com_flo_util_HRest2Func_HRest
(JNIEnv * env, jclass j, jstring jtrainlist,jstring jdestDir,jstring jhmmFile,jstring juserid,jstring jlabPath) {

	char* trainlist = (char*) (*env)->GetStringUTFChars(env,jtrainlist, 0);
	char* destDir = (char*) (*env)->GetStringUTFChars(env,jdestDir, 0);
	char* hmmFile = (char*) (*env)->GetStringUTFChars(env,jhmmFile, 0);
	char* userid = (char*) (*env)->GetStringUTFChars(env,juserid, 0);
	char* labPath = (char*) (*env)->GetStringUTFChars(env,jlabPath, 0);
	int argc=12;
	char *argv[12];
	argv[0]="HRest";
	argv[1]="-S";
	argv[2]=trainlist;
	argv[3]="-M";
	argv[4]=destDir;
	argv[5]="-H";
	argv[6]=hmmFile;
	argv[7]="-l";
	argv[8]=userid;
	argv[9]="-L";
	argv[10]=labPath;
	argv[11]=userid;
	main_HRest(argc, argv);
}
