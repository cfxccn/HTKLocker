package com.flo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.flo.model.User;

import android.content.Context;
import android.os.Environment;

public class FileService {
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
	String dictFile;

	Context context;

	public FileService(Context context) {
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
				// FileUtil.copyFile(inputStream, appRoot + "/config");
				File conf = new File(appRoot + "/config");
				FileUtils.copyInputStreamToFile(inputStream, conf);
			} catch (IOException e) {
			}
			try {
				File hmm0 = new File(appRoot + "/hmm0");
				FileUtils.forceMkdir(hmm0);
				hmm0Path = hmm0.getAbsolutePath();

				File hmm1 = new File(appRoot + "/hmm1");
				FileUtils.forceMkdir(hmm1);
				hmm1Path = hmm1.getAbsolutePath();

				File hmm2 = new File(appRoot + "/hmm2");
				FileUtils.forceMkdir(hmm2);
				hmm2Path = hmm2.getAbsolutePath();

				File proto = new File(appRoot + "/proto");
				FileUtils.forceMkdir(proto);
				protoPath = proto.getAbsolutePath();

				File mfcc = new File(appRoot + "/mfcc");
				FileUtils.forceMkdir(mfcc);
				mfccPath = mfcc.getAbsolutePath();

				File lab = new File(appRoot + "/lab");
				FileUtils.forceMkdir(lab);
				labPath = lab.getAbsolutePath();

				File trainWav = new File(appRoot + "/trainwav");
				FileUtils.forceMkdir(trainWav);
				trainWavPath = trainWav.getAbsolutePath();

				File testWav = new File(appRoot + "/testwav");
				FileUtils.forceMkdir(testWav);
				testWavPath = testWav.getAbsolutePath();

			} catch (IOException e) {
				appRoot = null;
			}
		} else {
			appRoot = null;
		}
	}

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

	public String getConfigFilePath() {
		return appRoot + "/config";
	}

	public String getDictFilePath() {
		return appRoot + "/dict.txt ";
	}

	public String getSlfFilePath() {
		return appRoot + "/net.slf";
	}

	public String createLab(String userid) {
		// FileOutputStream fs = null;
		// String textString = "0 20000000 " + userid;
		getLabPath(userid);
		try {
			FileUtils.write(new File(labUserPath + "/" + userid + "_1.lab"),
					"0 20000000 " + userid);
			FileUtils.write(new File(labUserPath + "/" + userid + "_2.lab"),
					"0 20000000 " + userid);
			FileUtils.write(new File(labUserPath + "/" + userid + "_3.lab"),
					"0 20000000 " + userid);

			// fs = new FileOutputStream(labUserPath + "/" + userid + "_1.lab");
			// fs.write(textString.getBytes());
			// fs.close();
			// fs = new FileOutputStream(labUserPath + "/" + userid + "_2.lab");
			// fs.write(textString.getBytes());
			// fs.close();
			// fs = new FileOutputStream(labUserPath + "/" + userid + "_3.lab");
			// fs.write(textString.getBytes());
			// fs.close();
			// fs = new FileOutputStream(labUserPath + "/" + userid + "_4.lab");
			// fs.write(textString.getBytes());
			// fs.close();
			return labUserPath;
		} catch (Exception e) {
			return null;
		}
	}

	public String createWavList(String wavPath, String userid, boolean isTrain) {
		// getLabPath(userid);
		// FileOutputStream fs = null;
		// String textString1 = wavPath + "/" + userid + "_1.wav " + mfccPath
		// + "/" + userid + "_1.mfc \n";
		try {
			if (isTrain == true) {
				FileUtils.write(new File(appRoot + "/wavlist.txt"), wavPath
						+ "/" + userid + "_1.wav " + mfccPath + "/" + userid
						+ "_1.mfc \n" + wavPath + "/" + userid + "_2.wav "
						+ mfccPath + "/" + userid + "_2.mfc \n" + wavPath + "/"
						+ userid + "_3.wav " + mfccPath + "/" + userid
						+ "_3.mfc \n");
			} else {
				FileUtils.write(new File(appRoot + "/wavlist.txt"), wavPath
						+ "/"+userid+".wav " + mfccPath + "/"+userid+".mfc \n");
			}
			// fs = new FileOutputStream(appRoot + "/wavlist.txt");
			// fs.write(textString1.getBytes());
			// fs.close();
			return appRoot + "/wavlist.txt";
		} catch (Exception e) {
			return null;
		}
	}

	public String createProto(String userid) {
		try {
			InputStream inputStream = context.getAssets().open("proto");
			OutputStream outputStream = new FileOutputStream(protoPath
					+ "/hmm_" + userid);
			byte btHeader[] = new byte[34];
			inputStream.read(btHeader);
			outputStream.write(btHeader);
			outputStream.write(userid.getBytes());
			int c;
			byte bt[] = new byte[1024];
			while ((c = inputStream.read(bt)) > 0) {
				outputStream.write(bt, 0, c);
			}
			outputStream.close();
			return protoPath + "/hmm_" + userid;
		} catch (IOException e) {
			return null;
		}
	}

	public String createTrainList(String userid) {
		// FileOutputStream fs = null;
		// String textString1 = mfccPath + "/" + userid + "_1.mfc \n";
		// String textString2 = mfccPath + "/" + userid + "_2.mfc \n";
		// String textString3 = mfccPath + "/" + userid + "_3.mfc \n";
		// String textString4 = mfccPath + "/" + userid + "_4.mfc \n";
		List<String> textStrings = new ArrayList<String>();
		textStrings.add(mfccPath + "/" + userid + "_1.mfc \n");
		textStrings.add(mfccPath + "/" + userid + "_2.mfc \n");
		textStrings.add(mfccPath + "/" + userid + "_3.mfc \n");

		try {
			FileUtils.writeLines(new File(appRoot + "/trainlist.txt"),
					textStrings);

			// fs = new FileOutputStream(appRoot + "/trainlist.txt");
			// fs.write(textString1.getBytes());
			// fs.write(textString2.getBytes());
			// fs.write(textString3.getBytes());
			// fs.write(textString4.getBytes());
			// fs.close();
			return appRoot + "/trainlist.txt";
		} catch (Exception e) {
			return null;
		}
	}

	public void deleteUser(String userid) {

		FileUtils.deleteQuietly(new File(getLabPath(userid)));

		FileUtils
				.deleteQuietly(new File(trainWavPath + "/" + userid + "_1.wav"));
		FileUtils
				.deleteQuietly(new File(trainWavPath + "/" + userid + "_2.wav"));
		FileUtils
				.deleteQuietly(new File(trainWavPath + "/" + userid + "_3.wav"));
		FileUtils.deleteQuietly(new File(mfccPath + "/" + userid + "_1.mfc"));
		FileUtils.deleteQuietly(new File(mfccPath + "/" + userid + "_2.mfc"));
		FileUtils.deleteQuietly(new File(mfccPath + "/" + userid + "_3.mfc"));
		FileUtils.deleteQuietly(new File(protoPath + "/hmm_" + userid));
		FileUtils.deleteQuietly(new File(hmm0Path + "/hmm_" + userid));
		FileUtils.deleteQuietly(new File(hmm1Path + "/hmm_" + userid));
		FileUtils.deleteQuietly(new File(hmm2Path + "/hmm_" + userid));
	}

	public String createGram(List<User> userList) {
		String gramFile = appRoot + "/gram.txt";
		String userString = "";
		if (userList.size() != 0) {
			StringBuilder userStringBuilder = new StringBuilder();
			for (User u : userList) {
				userStringBuilder.append("|");
				userStringBuilder.append(u.getNameId());
			}
			userString = userStringBuilder.toString().substring(1);
		}

		try {
			FileUtils.write(new File(gramFile), "$WORD= " + userString
					+ ";\n([$WORD])");
			return gramFile;
		} catch (IOException e) {
			return null;

		}

	}

	public String createDict(List<User> userList) {
		String dictFile = appRoot + "/dict.txt";
		StringBuilder userStringBuilder = new StringBuilder();
		if (userList.size() != 0) {
			for (User u : userList) {
				userStringBuilder.append(u.getNameId());
				userStringBuilder.append(" [");
				userStringBuilder.append(u.getNameId());
				userStringBuilder.append("] ");
				userStringBuilder.append(u.getNameId());
				userStringBuilder.append("\n");
			}
		}

		try {
			FileUtils.write(new File(dictFile), userStringBuilder.toString());
			return dictFile;
		} catch (IOException e) {
			return null;

		}

	}

	public String createAllMmf(List<User> userList) {
		String allMmfFile = appRoot + "/all.mmf";
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			outputStream = FileUtils.openOutputStream(new File(allMmfFile));
			if (userList.size() != 0) {
				for (User u : userList) {
					inputStream = FileUtils.openInputStream(FileUtils
							.getFile(getHmm2Path() + "/hmm_" + u.getNameId()));
					if (userList.indexOf(u) == 0) {
						int c;
						byte bt[] = new byte[1024];
						while ((c = inputStream.read(bt)) > 0) {
							outputStream.write(bt, 0, c);
						}
					} else {
						int c;
						byte bt[] = new byte[1024];
						inputStream.read(new byte[3]);
						while ((c = inputStream.read(bt)) > 0) {
							outputStream.write(bt, 0, c);
						}
					}

				}
			}
			outputStream.close();
			return allMmfFile;
		} catch (IOException e) {
			return null;
		}
	}
}
