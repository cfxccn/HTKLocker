package com.flo.util;

public class HClearFunc {
	static {
		System.loadLibrary("HClear");
	}
	public static void exec() {
		/*
		 * Hcopy -A -D -C training/analysis.conf -S
		 * training/targetlist_train.txt
		 */
		
		HClear(null);
	}
	/** 
	* @param 
	* @return 
	*/ 
	private native static void HClear(String s );
}
