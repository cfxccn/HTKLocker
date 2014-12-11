package com.flo.util;

public class HTKFunc {
	static {
		System.loadLibrary("libHTK");
	}

	private native static void HCopy(String path);
	private native static void HInit(String path);
	private native static void HRest(String path);
	private native static void HVite(String path);

}
