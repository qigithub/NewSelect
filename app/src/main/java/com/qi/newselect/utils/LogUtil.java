package com.qi.newselect.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 日志显示工具类，通过控制isShowingLog的值控制所有日志是否输出
 *
 * 
 */
public class LogUtil {

	private static final String TAG = "LogUtil";
	public static boolean isShowingLog = true;
	public static String LOG_FILE_DIR="waterWorker/";
	public static String LOG_FILE =  "log.txt";
	public static final int RESERVED_LOG_LEN = 1024 * 1024;
	public static final String yyyy_MM_dd = "yyyy_MM_dd";
	public static final int FILE_SIZE = 50;
	public static final String UTF8="UTF-8";
	public static final String GBK="GBK";

	public static void i(String tag, String msg) {
		if (isShowingLog) {
			Log.i(tag, msg);
			String fileFullPath = getExternalStoragePath() + "/"+LOG_FILE_DIR+ getLogDate(new Date())+ "_" + LOG_FILE;
			String logMsg = "[info] [tag = " + tag + "] " + msg + "\n";
		}
	}

	public static void e(String tag, String msg) {
		if (isShowingLog) {
			Log.e(tag, msg);
			String fileFullPath = getExternalStoragePath() + "/" + LOG_FILE_DIR+ getLogDate(new Date())+ "_" + LOG_FILE;
			String logMsg = "[error] [tag = " + tag + "] " + msg + "\n";
			writeToFile(fileFullPath, logMsg);
//			LogWriter.writeLog(tag,msg);
		}
	}

	public static void d(String tag, String msg) {
		if (isShowingLog) {
			Log.d(tag, msg);
			String fileFullPath = getExternalStoragePath() + "/" + LOG_FILE_DIR+ getLogDate(new Date())+ "_" + LOG_FILE;
			String logMsg = "[debug] [tag = " + tag + "] " + msg + "\n";
			writeToFile(fileFullPath, logMsg);
//			LogWriter.writeLog(tag,msg);
		}
	}

	public static String getExternalStoragePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static void writeToFile(final String fileFullNmae, final String content) {

		synchronized (LogUtil.class) {
			Runnable runnable = new Runnable() {
				public void run() {
					String sdStatus = Environment.getExternalStorageState();
					if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                        Log.d(TAG, "sd卡不可用");
                        return;
                    }

					try {
                        String[] pathNode = fileFullNmae.split("/");
                        String dir = fileFullNmae.replace("/" + pathNode[pathNode.length - 1], "");
                        File path = new File(dir);
                        if (!path.exists()) {
                            path.mkdirs();
                        }

                        RandomAccessFile randomFile = new RandomAccessFile(fileFullNmae, "rw");
                        long fileLength = randomFile.length();
                        if (fileLength > RESERVED_LOG_LEN * FILE_SIZE) {
                            byte[] preData = new byte[RESERVED_LOG_LEN];
                            randomFile.seek(fileLength - RESERVED_LOG_LEN);
                            randomFile.read(preData);
                            randomFile.close();
                            File deleteFile = new File(fileFullNmae);
                            deleteFile.delete();
                            randomFile = new RandomAccessFile(fileFullNmae, "rw");
                            randomFile.seek(0);
                            String preDataStr = new String(preData);
                            randomFile.write(preDataStr.getBytes());
//							randomFile.writeBytes(preDataStr);
                        }
                        fileLength = randomFile.length();
                        randomFile.seek(fileLength);

                        Date nowTime=new Date();
                        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
                        String timeFromat = time.format(nowTime);
						String msg = "[" + timeFromat + "] " +
								content;
//                        randomFile.writeBytes(content);
						randomFile.write(
//								URLDecoder.decode(msg,UTF8).getBytes());
								msg.getBytes());
                        randomFile.close();

                    } catch (Exception e) {
                        Log.d(TAG, "sd卡写入失败");
                    }
				}
			};
			new Thread(runnable).start();
		}
	}
	
	public static String getException(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		String exceptionMessage = "";
		exceptionMessage = sw.toString();
		
		return exceptionMessage;
	}
	
	public static void autoSetDebugOrReleaseMode(Context context) {
		if (isApkDebugable(context)) {
			isShowingLog = true;
		} else {
			isShowingLog = false;
		}
	}
	private static boolean isApkDebugable(Context context) {
        try {  
            ApplicationInfo info= context.getApplicationInfo();
                return (info.flags& ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {
              
        }  
        return false;  
    }

	public static String getLogDate(Date date){
		SimpleDateFormat time = new SimpleDateFormat(yyyy_MM_dd);
		return time.format(date);
	}





	/**
	 * 根据日期获得所在周的日期
	 * @param mdate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static List<Date> dateToWeek(Date mdate) {
		int b = mdate.getDay();
		Date fdate;
		List<Date> list = new ArrayList<Date>();
		Long fTime = mdate.getTime() - b * 24 * 3600000;
		for (int a = 1; a <= 7; a++) {
			fdate = new Date();
			fdate.setTime(fTime + (a * 24 * 3600000));
			list.add(a-1, fdate);
		}
		return list;
	}


	/**
	 * 删除一周前的那一天的log
	 * @param mdate 当天日期
     */
	@SuppressWarnings("deprecation")
	public static void dateToWeekAfter(final Date mdate) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
				try {
					long time = mdate.getTime();
//		int b = mdate.getDay();星期几
					Date fdate;
//		List<Date> list = new ArrayList<Date>();
					SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd);
					long fTime = (long) time - (24 * 3600000 * 8);
//		for (int a = 1; a <= 7; a++) {
					fdate = new Date();
					fdate.setTime(fTime + (1 * 24 * 3600000));
//			list.add(a-1, fdate);
					Log.i(TAG,"dateToWeekAfter = "+ format.format(fTime));
					String path = getExternalStoragePath() + "/"+LOG_FILE_DIR+ format.format(fTime)+ "_" + LOG_FILE;
					File file = new File(path);
					if (file.exists()){
                        file.delete();
                    }else {
						Log.i(TAG,"不存在 log filepath ="+ path);
					}
//		}
				} catch (Exception e) {
					e.printStackTrace();

				}
//			}
//		}).start();
		return ;
	}

    public static void logSendBroadcastName(String tag, Intent intent) {
        LogUtil.i(tag," sendBroadcast = "+ intent.getAction());
    }
}
