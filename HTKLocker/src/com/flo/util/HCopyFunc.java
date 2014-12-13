package com.flo.util;

public class HCopyFunc {

	public static void exec(String configFilePath, String wavlistPath) {
		/*
		 * Hcopy -A -D -C training/analysis.conf -S
		 * training/targetlist_train.txt
		 */
		
		System.loadLibrary("HCopy");
		HCopy(configFilePath, wavlistPath);
	}
	/** 
	* @param 
	* @return 
	*/ 
	private native static void HCopy(String configFilePath, String wavlistPath);
}
