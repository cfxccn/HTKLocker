package com.flo.util;

public class HTK {
	static {
		System.loadLibrary("A");
	}

	public static void mfcc(String configFilePath, String wavlistPath) {
		/*
		 * Hcopy -A -D -C training/analysis.conf -S
		 * training/targetlist_train.txt
		 */
		HCopy(configFilePath, wavlistPath);
	}

	public static void train(String appRoot, String trainlistPath,
			String protoFilePath, String userid, String labPath) {
		/*
		 * HInit -A -D -T 1 -S ccctrainlist -M hmm0 -H proto/hmm_ccc -l ccc -L
		 * lab/ccc ccc
		 */
	}

	private native static void HCopy(String configFilePath, String wavlistPath);

	public native static void HInit(String path);

	public native static void HRest(String path);

	public native static void HVite(String path);
}
