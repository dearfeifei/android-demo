package forever.foreverandroiddemo.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import forever.foreverandroiddemo.R;

/**
 * 文件下载类
 */
public class AppFileDownUtils extends Thread {

    private Context mContext;
    private Handler mHandler;
    private String mDownloadUrl;
    private String mFileName;
    private Message msg;

    private final String APP_FOLDER = "DownDemo";
    private final String APK_FOLDER = "apkFile";

    public static final int MSG_UNDOWN = 0;
    public static final int MSG_DOWNING = 1;
    public static final int MSG_FINISH = 1;
    public static final int MSG_FAILURE = 2;

    private NotificationManager mNotifManager;
    private Notification mDownNotification;
    private RemoteViews mContentView;
    private PendingIntent mDownPendingIntent;

    public AppFileDownUtils(Context context, Handler handler,
                            String downloadUrl, String fileName) {
        mContext = context;
        mHandler = handler;
        mDownloadUrl = downloadUrl;
        mFileName = fileName;
        mNotifManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        msg = new Message();
    }

    @Override
    public void run() {
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {

                Message downingMsg = new Message();
                downingMsg.what = MSG_DOWNING;
                mHandler.sendMessage(downingMsg);
                File sdcardDir = Environment.getExternalStorageDirectory();
                File folder = new File(sdcardDir + File.separator + APP_FOLDER
                        + File.separator + APK_FOLDER);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                File saveFilePath = new File(folder, mFileName);
                if (saveFilePath.exists()) {
                    saveFilePath.createNewFile();
                }

                System.out.println(saveFilePath);
                mDownNotification = new Notification(
                        android.R.drawable.stat_sys_download,
                        "正在下载", System.currentTimeMillis());
                mDownNotification.flags = Notification.FLAG_ONGOING_EVENT;
                mDownNotification.flags = Notification.FLAG_AUTO_CANCEL;
                mContentView = new RemoteViews(mContext.getPackageName(),
                        R.layout.activity_notifications_loading);
                mContentView.setImageViewResource(R.id.downLoadIcon,
                        android.R.drawable.stat_sys_download);
                mDownPendingIntent = PendingIntent.getActivity(mContext, 0,
                        new Intent(), 0);
                boolean downSuc = downloadFile(mDownloadUrl, saveFilePath);
                if (downSuc) {
                    msg.what = MSG_FINISH;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(Uri.fromFile(saveFilePath),
                            "application/vnd.android.package-archive");
                    PendingIntent contentIntent = PendingIntent.getActivity(
                            mContext, 0, intent, 0);

                    //					notification.setLatestEventInfo(mContext,
//							"下载成功", null, contentIntent);

                    // jonathan 替换掉过时的 setLatestEventInfo
                    Notification.Builder builder = new Notification.Builder(mContext)
                            .setAutoCancel(false)
                            .setContentTitle("一起点拨")
                            .setContentText("下载成功")
                            .setContentIntent(contentIntent)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setWhen(System.currentTimeMillis())
                            .setOngoing(true);
                    Notification notification = builder.getNotification();


                    mNotifManager.notify(R.drawable.ic_launcher, notification);
                } else {
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();

                    msg.what = MSG_FAILURE;


                    PendingIntent contentIntent = PendingIntent.getActivity(
                            mContext, 0, new Intent(), 0);
//                    Notification notification = new Notification(
//                            android.R.drawable.stat_sys_download_done,
//                            "下载失败", System.currentTimeMillis());
//                    notification.flags = Notification.FLAG_AUTO_CANCEL;
//                    notification.setLatestEventInfo(mContext,
//                            "下载失败", null, contentIntent);


                    Notification.Builder builder = new Notification.Builder(mContext)
                            .setAutoCancel(false)
                            .setContentTitle("一起点拨")
                            .setContentText("下载失败")
                            .setContentIntent(contentIntent)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setWhen(System.currentTimeMillis())
                            .setOngoing(true);
                    Notification notification = builder.getNotification();


                    mNotifManager.notify(R.drawable.ic_launcher, notification);
                }

            } else {
                Toast.makeText(mContext, Environment.getExternalStorageState(),
                        Toast.LENGTH_SHORT).show();
                msg.what = MSG_FAILURE;
            }
        } catch (Exception e) {
            Log.e("TAG", "AppFileDownUtils catch Exception:", e);
            msg.what = MSG_FAILURE;
        } finally {
            mHandler.sendMessage(msg);
        }
    }

    public boolean downloadFile(String downloadUrl, File saveFilePath) {
        int fileSize = -1;
        int downFileSize = 0;
        boolean result = false;
        int progress = 0;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (null == conn) {
                return false;
            }
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                fileSize = conn.getContentLength();
                System.out.println("fileSize==>" + fileSize);

                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(saveFilePath);
                byte[] buffer = new byte[1024];
                int i = 0;
                int tempProgress = -1;
                while ((i = is.read(buffer)) != -1) {
                    downFileSize = downFileSize + i;
                    progress = (int) (downFileSize * 100.0 / fileSize);
                    fos.write(buffer, 0, i);

                    synchronized (this) {
                        if (downFileSize == fileSize) {
                            mNotifManager.cancel(R.id.downLoadIcon);
                        } else if (tempProgress != progress) {
                            mContentView.setTextViewText(R.id.progressPercent,
                                    progress + "%");
                            mContentView.setProgressBar(R.id.downLoadprogress,
                                    100, progress, false);
                            mDownNotification.contentView = mContentView;
                            mDownNotification.contentIntent = mDownPendingIntent;
                            mNotifManager.notify(R.id.downLoadIcon,
                                    mDownNotification);
                            tempProgress = progress;
                        }
                    }
                }
                fos.flush();
                fos.close();
                is.close();
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            result = false;
            Log.e("TAG", "downloadFile catch Exception:", e);
        }
        return result;
    }
}