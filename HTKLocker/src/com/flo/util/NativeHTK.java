package com.flo.util;

import java.io.IOException;
import java.util.List;




import com.flo.accessobject.FileAccessObject;
import com.flo.accessobject.UserAccessObject;
import com.flo.model.User;

public class NativeHTK {

	public static void createMFCC(FileAccessObject fileAccessObject,
			String wavPath, String userid, boolean isTrain) {
		String wavlist = fileAccessObject.createWavList(wavPath, userid,
				isTrain);
		HCopyFunc.exec(fileAccessObject.getConfigFilePath(), wavlist);
		// fileService.copyMfcc(userid);
	}

	public static void train(final FileAccessObject fileAccessObject,
			final String userid, List<Long> timeList, final String trainlist,
			final String protoFile, final String labUserPath) {
		// String labUserPath = fileAccessObject.createLab(userid,timeList);
		// String protoFile = fileAccessObject.createProto(userid);
		// String trainlist = fileAccessObject.createTrainList(userid);
		HInitFunc.exec(trainlist, fileAccessObject.getHmm0Path(), protoFile,
				userid, labUserPath);
	}

	public static void train2(final FileAccessObject fileAccessObject,
			final String userid, List<Long> timeList, final String trainlist,
			String protoFile, final String labUserPath) {
		// String labUserPath = fileAccessObject.createLab(userid,timeList);
		// String protoFile = fileAccessObject.createProto(userid);
		// String trainlist = fileAccessObject.createTrainList(userid);

		HRestFunc.exec(trainlist, fileAccessObject.getHmm1Path(),
				fileAccessObject.getHmm0Path() + "/hmm_" + userid, userid,
				labUserPath);

	}

	public static void train3(final FileAccessObject fileAccessObject,
			final String userid, List<Long> timeList, final String trainlist,
			String protoFile, final String labUserPath) {

		// HRest2Func.exec(trainlist, fileAccessObject.getHmm2Path(),
		// fileAccessObject.getHmm1Path() + "/hmm_" + userid, userid,
		// labUserPath);

		HRest2Func.exec(trainlist, fileAccessObject.getHmm2Path(),
				fileAccessObject.getHmm1Path() + "/hmm_" + userid, userid,
				labUserPath);
	}

	// public static void createSlf(String gramFilePath, String slfFilePath) {
	// HParseFunc.exec(gramFilePath, slfFilePath);
	// }

	public static void test(FileAccessObject fileAccessObject,
			UserAccessObject userAccessObject, String USERID)
			throws IOException, InterruptedException {
		List<User> userList = userAccessObject.getTrainedUserList();
		String gramFile = fileAccessObject.createGram(userList);
		HParseFunc.exec(gramFile, fileAccessObject.getSlfFilePath());
		String dictFile = fileAccessObject.createDict(userList);
		String allMmfFile = fileAccessObject.createAllMmf(userList);
		String netSlfFile = fileAccessObject.getSlfFilePath();
		String hmmListFile = fileAccessObject.createHmmListFile(userList);
		String resultFile = fileAccessObject.getResultFilePath();
		String mfcFile = fileAccessObject.getMfccPath() + "/" + USERID + ".mfc";
		//String hViteE = fileAccessObject.getHViteE();

		HViteFunc.exec( allMmfFile, resultFile, netSlfFile, dictFile,
				hmmListFile, mfcFile);
	//	Thread.sleep(1000);

	}

	public static void clear() {
		HClearFunc.exec();
	}

}
