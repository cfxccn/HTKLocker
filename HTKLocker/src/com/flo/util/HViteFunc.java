package com.flo.util;

public class HViteFunc {
	static {
		System.loadLibrary("HVite");
	}

	public static void exec(String allMmfFile, String resultFile,
			String netSlfFile, String dictFile, String hmmListFile,
			String mfcFile) {
		/*
		 * HVite -A -D -T 1 -H all.mmf -i reco.mlf -w net.slf dict.txt
		 * hmmlist.txt mfcc/lock5.mfc
		 */
		HVite(allMmfFile, resultFile, netSlfFile, dictFile, hmmListFile,
				mfcFile);
	}

	/**
	 * @param
	 * @return
	 */
	private native static void HVite(String allMmfFile, String resultFile,
			String netSlfFile, String dictFile, String hmmListFile,
			String mfcFile);
}
