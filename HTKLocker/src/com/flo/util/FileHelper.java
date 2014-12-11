package com.flo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;

public class FileHelper {
	/**
	 * File structure ./- |-proto- hmmperson.mmf... |-wav- person1.wav
	 * person2.wav person3.wav... |-mfcc- person1.mfc person2.mfc person3.mfc...
	 * |-lab- |-person person1.lab person2.lab person3.lab... |-hmm0 |-hmm1
	 * |-hmm2 -persontrainlist.txt -dict.txt -net.slf -wavlist.txt
	 * 
	 */
	String appRoot;
	String hmm0Path;
	String hmm1Path;
	String hmm2Path;
	String protoPath;
	String mfccPath;
	String labPath;
	String labUserPath;
	String trainWavPath;
	String testWavPath;
	Context context;

	public String getAppRoot() {
		return appRoot;
	}

	public String getHmm0Path() {
		return hmm0Path;
	}

	public String getHmm1Path() {
		return hmm1Path;
	}

	public String getHmm2Path() {
		return hmm2Path;
	}

	public String getProtoPath() {
		return protoPath;
	}

	public String getMfccPath() {
		return mfccPath;
	}

	public String getLabPath(String userid) {
		File labUser = new File(labPath + "/" + userid);
		if (!labUser.exists()) {
			labUser.mkdirs();
		}
		labUserPath = labUser.getAbsolutePath();
		return labUserPath;
	}

	public String getTrainWavPath() {
		return trainWavPath;
	}

	public String getTestWavPath() {
		return testWavPath;
	}

	public FileHelper(Context context) {
		this.context = context;
		File sdDir;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = context.getExternalFilesDir(null);
			appRoot = sdDir.getAbsolutePath();
			InputStream inputStream;
			try {
				inputStream = context.getAssets().open("config");
				FileUtil.copyFile(inputStream, appRoot + "/config");
			} catch (IOException e) {
			}

			File hmm0 = new File(appRoot + "/hmm0");
			if (!hmm0.exists()) {
				hmm0.mkdirs();
			}
			hmm0Path = hmm0.getAbsolutePath();

			File hmm1 = new File(appRoot + "/hmm1");
			if (!hmm1.exists()) {
				hmm1.mkdirs();
			}
			hmm1Path = hmm1.getAbsolutePath();

			File hmm2 = new File(appRoot + "/hmm2");
			if (!hmm2.exists()) {
				hmm2.mkdirs();
			}
			hmm2Path = hmm2.getAbsolutePath();

			File proto = new File(appRoot + "/proto");
			if (!proto.exists()) {
				proto.mkdirs();
			}
			protoPath = proto.getAbsolutePath();

			File mfcc = new File(appRoot + "/mfcc");
			if (!mfcc.exists()) {
				mfcc.mkdirs();
			}
			mfccPath = mfcc.getAbsolutePath();

			File lab = new File(appRoot + "/lab");
			if (!lab.exists()) {
				lab.mkdirs();
			}
			labPath = lab.getAbsolutePath();

			File trainWav = new File(appRoot + "/trainwav");
			if (!trainWav.exists()) {
				trainWav.mkdirs();
			}
			trainWavPath = trainWav.getAbsolutePath();

			File testWav = new File(appRoot + "/testWav");
			if (!testWav.exists()) {
				testWav.mkdirs();
			}
			testWavPath = testWav.getAbsolutePath();

		} else {
			appRoot = null;
		}
	}

	public void copyWav(String userid) {
		FileUtil.copyFile(trainWavPath + "/" + userid + "-1.wav", trainWavPath
				+ "/" + userid + "-2.wav");
		FileUtil.copyFile(trainWavPath + "/" + userid + "-1.wav", trainWavPath
				+ "/" + userid + "-3.wav");
		FileUtil.copyFile(trainWavPath + "/" + userid + "-1.wav", trainWavPath
				+ "/" + userid + "-4.wav");
	}

	public void clearWav(String userid) {
		FileUtil.deleteFile(trainWavPath + "/" + userid + "-2.wav");
		FileUtil.deleteFile(trainWavPath + "/" + userid + "-3.wav");
		FileUtil.deleteFile(trainWavPath + "/" + userid + "-4.wav");

	}

	public boolean createLab(String userid) {
		getLabPath(userid);
		FileOutputStream fs = null;
		String textString = "0 20000000 " + userid;
		try {
			fs = new FileOutputStream(labUserPath + "/" + userid + "-1.lab");
			fs.write(textString.getBytes());
			fs.close();
			fs = new FileOutputStream(labUserPath + "/" + userid + "-2.lab");
			fs.write(textString.getBytes());
			fs.close();
			fs = new FileOutputStream(labUserPath + "/" + userid + "-3.lab");
			fs.write(textString.getBytes());
			fs.close();
			fs = new FileOutputStream(labUserPath + "/" + userid + "-4.lab");
			fs.write(textString.getBytes());
			fs.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean createWavList(String userid) {
		getLabPath(userid);
		FileOutputStream fs = null;
		String textString1 = trainWavPath + "/" + userid + "-1.wav " + mfccPath
				+ "/" + userid + "-1.mfc \n";
		String textString2 = trainWavPath + "/" + userid + "-2.wav " + mfccPath
				+ "/" + userid + "-2.mfc \n";
		String textString3 = trainWavPath + "/" + userid + "-3.wav " + mfccPath
				+ "/" + userid + "-3.mfc \n";
		String textString4 = trainWavPath + "/" + userid + "-4.wav " + mfccPath
				+ "/" + userid + "-4.mfc \n";

		try {
			fs = new FileOutputStream(appRoot + "/wavlist.txt");
			fs.write(textString1.getBytes());
			fs.write(textString2.getBytes());
			fs.write(textString3.getBytes());
			fs.write(textString4.getBytes());
			fs.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean createProto(String userid) {
		try {
			InputStream inputStream = context.getAssets().open("proto");
			OutputStream fosto = new FileOutputStream(protoPath + "/hmm-" + userid);
			byte btHeader[] = new byte[34];
			inputStream.read(btHeader);
			fosto.write(btHeader);
			fosto.write(userid.getBytes());
			int c;
			byte bt[] = new byte[1024];
			while ((c = inputStream.read(bt)) > 0) {
			fosto.write(bt, 0, c);
			}
			fosto.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
