package com.flo.util;

public class HInitFunc {


	public static void exec(String trainlist, String hmm0Path,
			String protoFile, String userid, String labUserPath) {
		System.loadLibrary("HInit");

		HInit(trainlist, hmm0Path, protoFile, userid, labUserPath);
		/*
		 * HInit -A -D -T 1 -S aaatrainlist -M hmm0 -H proto/hmm_aaa -l aaa -L
		 * lab/aaa aaa
		 */
	}

	private native static void HInit(String trainlist, String hmm0Path,
			String protoFile, String userid, String labUserPath);
}
