package forever.foreverandroiddemo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件下载类
 */
public class ApkUpdateUtils {

    public interface OnDownLoadListener {
        void onLoadSuccess(String saveApkUrl);
        void onLoadFailure(String errorMsg);
    }

    public static void downLoadApk(final String version_code, final String apkUrl, final OnDownLoadListener onDownLoadListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {

                        File sdcardDir = Environment.getExternalStorageDirectory();
                        File folder = new File(sdcardDir + File.separator + "apkFile");
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                        String fileName = version_code+apkUrl.substring(apkUrl.lastIndexOf("/"));
                        File saveFilePath = new File(folder, fileName);
                        if (saveFilePath.exists()) {
                            saveFilePath.createNewFile();
                        }
//                        LogUtil.d("====saveFilePath",saveFilePath.getPath());
                        boolean downSuc = downloadFile(apkUrl, saveFilePath, onDownLoadListener);

                        if (downSuc) {

                            onDownLoadListener.onLoadSuccess(saveFilePath.getPath());
//                            LogUtil.d("====onDownLoadListener","onLoadSuccess");
                        } else {

                            onDownLoadListener.onLoadFailure("下载失败");
//                            LogUtil.d("====onDownLoadListener","onLoadFailure");
                        }
                    } else {
                        onDownLoadListener.onLoadFailure("文件创建失败");
                    }
                } catch (Exception e) {
                    Log.e("TAG", "AppFileDownUtils catch Exception:", e);
                }
            }
        }).start();

    }


    public static boolean downloadFile(String downloadUrl, File saveFilePath, OnDownLoadListener onDownLoadListener) {
        int fileSize = -1;
        int downFileSize = 0;
        boolean result = false;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (null == conn) {
                onDownLoadListener.onLoadFailure("连接失败");
                return false;
            }
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                fileSize = conn.getContentLength();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(saveFilePath);
                byte[] buffer = new byte[1024];
                int i = 0;
                while ((i = is.read(buffer)) != -1) {
                    downFileSize = downFileSize + i;
                    fos.write(buffer, 0, i);
                }
                fos.flush();
                fos.close();
                is.close();
                result = true;
            } else {
                onDownLoadListener.onLoadFailure("请求失败");
                result = false;
            }
//            LogUtil.d("====下载","成功");
        } catch (Exception e) {
            result = false;
            Log.e("TAG", "downloadFile catch Exception:", e);
//            LogUtil.d("====下载","错误 "+e.getMessage());
            onDownLoadListener.onLoadFailure(e.getMessage());
        }
        return result;
    }

    /**
     * 安装apk
     */
    public static void installApk(Context context,String path) {
        File apkfile = new File(path);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        context.startActivity(i);

    }
}