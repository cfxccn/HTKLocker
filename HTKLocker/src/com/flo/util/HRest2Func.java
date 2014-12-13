package com.flo.util;

public class HRest2Func {



	public static void exec(String trainlist, String hmm2Path,
			String hmm1File, String userid, String labUserPath) {
		// HRest -A -D -T 1 -S aaatrainlist -M hmm1 -H hmm0/hmm_aaa -l aaa -L
		// lab/aaa aaa
		System.loadLibrary("HRest2");
		HRest(trainlist, hmm2Path, hmm1File, userid, labUserPath);
	}
	// HRest -A -D -T 1 -S aaatrainlist -M hmm1 -H hmm0/hmm_aaa -l aaa -L
	// lab/aaa aaa

	/**
	 * @param
	 * @return
	 */
	private native static void HRest(String trainlist, String hmm2Path,
			String hmm1File, String userid, String labUserPath);
}
