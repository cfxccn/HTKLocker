package com.flo.util;

import java.io.IOException;

public class HViteFunc {
//	static {
//		System.loadLibrary("HVite");
//	}

	public static void exec1(String hViteE,String allMmfFile, String resultFile,
			String netSlfFile, String dictFile, String hmmListFile,
			String mfcFile) throws IOException {
		String[] argv = new String[10];
		argv[0] = hViteE;
		argv[1] = "-H";
		argv[2] = allMmfFile;
		argv[3] = "-i";
		argv[4] = resultFile;
		argv[5] = "-w";
		argv[6] = netSlfFile;
		argv[7] = dictFile;
		argv[8] = hmmListFile;
		argv[9] = mfcFile;

		Runtime.getRuntime().exec(argv);
	}
//
//	public static void exec(String allMmfFile, String resultFile,
//			String netSlfFile, String dictFile, String hmmListFile,
//			String mfcFile) throws IOException {
//		/*
//		 * HVite -A -D -T 1 -H all.mmf -i reco.mlf -w net.slf dict.txt
//		 * hmmlist.txt mfcc/lock5.mfc
//		 */
//		HVite(allMmfFile, resultFile, netSlfFile, dictFile, hmmListFile,
//				mfcFile);
//
//		// String[] argv = new String[10];
//		// argv[0]=hViteE;
//		// argv[1]="-H";
//		// argv[2]=allMmfFile;
//		// argv[3]="-i";
//		// argv[4]=resultFile;
//		// argv[5]="-w";
//		// argv[6]=netSlfFile;
//		// argv[7]=dictFile;
//		// argv[8]=hmmListFile;
//		// argv[9]=mfcFile;
//		// Runtime.getRuntime().exec(argv);
//
//	}
//
//	private native static void HVite(String allMmfFile, String resultFile,
//			String netSlfFile, String dictFile, String hmmListFile,
//			String mfcFile);
}
