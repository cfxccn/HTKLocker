package com.flo.util;

public class HCopyFunc {
	static {
		System.loadLibrary("HCopy");
	}
	public static void exec(String configFilePath, String wavlistPath) {
		/*
		 * Hcopy -A -D -C training/analysis.conf -S
		 * training/targetlist_train.txt
		 */
		
		HCopy(configFilePath, wavlistPath);
	}
	/** 
	* @param 
	* @return 
	*/ 
	private native static void HCopy(String configFilePath, String wavlistPath);
}
