package com.flo.util;


import com.flo.service.FileService;

public class TrainTest {

	public static void createMFCC(FileService fileService, String wavPath,
			String userid,boolean isTrain) {
		String wavlist = fileService.createWavList(wavPath, userid,isTrain);
		HCopyFunc.exec(fileService.getConfigFilePath(), wavlist);
	//	fileService.copyMfcc(userid);
	}

	public static void train(FileService fileService, String userid) {
		String labUserPath = fileService.createLab(userid);
		String protoFile = fileService.createProto(userid);
		String trainlist = fileService.createTrainList(userid);
		HInitFunc.exec(trainlist, fileService.getHmm0Path(), protoFile, userid,
				labUserPath);

		HRestFunc.exec(trainlist, fileService.getHmm1Path(),
				fileService.getHmm0Path() + "/hmm_" + userid, userid,
				labUserPath);
		HRest2Func.exec(trainlist, fileService.getHmm2Path(),
				fileService.getHmm1Path() + "/hmm_" + userid, userid,
				labUserPath);
	}

	public static void clear() {
		HClearFunc.exec();

	}

}
