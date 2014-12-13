package com.flo.util;

public class HRestFunc {
	static {
		System.loadLibrary("HRest");
	}

	public static void exec(String trainlist, String hmm1Path,
			String hmm0File, String userid, String labUserPath) {
		// HRest -A -D -T 1 -S aaatrainlist -M hmm1 -H hmm0/hmm_aaa -l aaa -L
		// lab/aaa aaa
		HRest(trainlist, hmm1Path, hmm0File, userid, labUserPath);
	}

	// HRest -A -D -T 1 -S aaatrainlist -M hmm1 -H hmm0/hmm_aaa -l aaa -L
	// lab/aaa aaa

	/**
	 * @param
	 * @return
	 */
	private native static void HRest(String trainlist, String hmm1Path,
			String hmm0File, String userid, String labUserPath);
}
