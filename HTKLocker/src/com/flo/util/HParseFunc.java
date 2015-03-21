package com.flo.util;

public class HParseFunc {
	static {
		System.loadLibrary("HParse");
	}
	public static void exec(String gramFilePath, String slfFilePath) {
		/* 
		 * HParse -A -D -T 1 gram.txt net.slf
		 */
		HParse( gramFilePath, slfFilePath);
	}
	/** 
	* @param 
	* @return 
	*/ 
	private native static void HParse(String gramFilePath, String slfFilePath);
}
