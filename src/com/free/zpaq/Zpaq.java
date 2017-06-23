package com.free.zpaq;
import java.io.*;
import android.util.*;

public class Zpaq
{
	private static String JNI_TAG = "Zpaq";
	
	public native int runZpaq(String... args);
	public native String stringFromJNI(String outfile, String infile);
	public native void closeStreamJNI();
	
	static {
        System.loadLibrary("zpaq");
    }
	
	public static final String PRIVATE_PATH = "/sdcard/.com.free.p7zip";
	private String mOutfile = PRIVATE_PATH + "/7zaOut.txt";
	private String mInfile = PRIVATE_PATH + "/7zaIn.txt";
	private String listFile = PRIVATE_PATH + "/7zaFileList.txt";

	public Zpaq() {
		String sPath = PRIVATE_PATH;
		mOutfile = sPath + "/7zaOut.txt";
		mInfile = sPath + "/7zaIn.txt";
		listFile = sPath + "/7zaFileList.txt";
	}

	public Zpaq(String logPath) {
		mOutfile = logPath + "/7zaOut.txt";
		mInfile = logPath + "/7zaIn.txt";
		listFile = logPath + "/7zaFileList.txt";
	}

	public void initStream() throws IOException {
		resetFile(mOutfile);
		resetFile(mInfile);
		resetFile(listFile);
		stringFromJNI(mOutfile, mInfile);
	}

	private void resetFile(String f) throws IOException {
		File file = new File(f);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		} else {
			file.delete();
		}
		file.createNewFile();
	}
	
	
	
	
	public Object[] runZpaq(boolean showDebug, String... args) throws IOException {
		try {
			initStream();

			if (args == null && args.length == 0) {
				return new Object[] {2, new StringBuilder()};
			}
			
			Log.d(JNI_TAG, "Call runZpaq(): " + args);
			
			int ret = runZpaq(args);
			Log.d(JNI_TAG, "runZpaq() ret " + ret);
			FileReader fileReader = new FileReader(mOutfile);
			BufferedReader br = new BufferedReader(fileReader, 32768);
			StringBuilder sb = new StringBuilder();
			if (!showDebug) {
				while (br.ready()) {
					sb.append(br.readLine()).append("\n");
				}
			} else {
				String readLine;
				while (br.ready()) {
					readLine = br.readLine();
					Log.d(JNI_TAG, readLine);
					sb.append(readLine).append("\n");
				}
			}
			return new Object[] {ret, sb};
		} finally {
			closeStreamJNI();
		}
	}
}
