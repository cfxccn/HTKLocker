package com.flo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.flo.model.User;
import com.flo.service.FileService;
import com.flo.service.UserService;

public class NativeHTK {

	public static void createMFCC(FileService fileService, String wavPath,
			String userid, boolean isTrain) {
		String wavlist = fileService.createWavList(wavPath, userid, isTrain);
		HCopyFunc.exec(fileService.getConfigFilePath(), wavlist);
		// fileService.copyMfcc(userid);
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

//	public static void createSlf(String gramFilePath, String slfFilePath) {
//		HParseFunc.exec(gramFilePath, slfFilePath);
//	}

	public static void test(FileService fileService, UserService userService,String USERID) {
		List<User> userList = userService.getTrainedUserList();
		String gramFile = fileService.createGram(userList);
		HParseFunc.exec(gramFile, fileService.getSlfFilePath());
		String dictFile = fileService.createDict(userList);
		String allMmfFile = fileService.createAllMmf(userList);	
		String netSlfFile=fileService.getSlfFilePath();
		String hmmListFile=fileService.createHmmListFile(userList);
		String resultFile=fileService.getResultFilePath();
		String mfcFile=fileService.getMfccPath()+"/"+USERID+".mfc";
		String hViteE=fileService.getHviteE();

		String[] argv = new String[10];
		argv[0]=hViteE;
		argv[1]="-H";
		argv[2]=allMmfFile;
		argv[3]="-i";
		argv[4]=resultFile;
		argv[5]="-w";
		argv[6]=netSlfFile;
		argv[7]=dictFile;
		argv[8]=hmmListFile;
		argv[9]=mfcFile;
		
		try {
			Runtime.getRuntime().exec(argv);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		//HViteFunc.exec(allMmfFile, resultFile, netSlfFile, dictFile, hmmListFile, mfcFile);
		
		
		
	}
}
