package com.flo.util;

public class HClearFunc {
	static {
		System.loadLibrary("HClear");
	}
	public static void exec() {
		HCopy();
	}
	private native static void HCopy();
}
