package com.flo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

	public static Boolean copyFile(String fromPath, String toPath) {
		try {
			InputStream fosfrom = new FileInputStream(fromPath);
			OutputStream fosto = new FileOutputStream(toPath);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c);
			}
			fosfrom.close();
			fosto.close();
			return true;
		} catch (IOException e) {
			// e.printStackTrace();
			return null;
		}
	}

	public static Boolean copyFile(InputStream fosfrom, String toPath) {
		try {
			OutputStream fosto = new FileOutputStream(toPath);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c);
			}
			fosfrom.close();
			fosto.close();
			return true;
		} catch (IOException e) {
			return null;
		}
	}

	public static Boolean deleteFile(String filePath) {
		File file = new File(filePath);
		return file.delete();
	}

}
