package forever.foreverandroiddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;

import java.io.IOException;
import java.io.InputStream;

public class Util {
    private static final int STROKE_WIDTH = 4;

    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  

    public static int dip2px(Context context,float dpValue){
    	 final float scale = context.getResources().getDisplayMetrics().density;
    	return (int) (dpValue * scale + 0.5f); 
    }
    
    
    
    // 从assets资源中获取图片
    public static Bitmap getBitmap(Context context, String filename) {

        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {

            InputStream is = am.open(filename);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static Bitmap toRoundBitmap(Context context, String filename) {
        Bitmap bitmap = getBitmap(context, filename);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            left = 0;
            bottom = width;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(4); // 线条大小
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);

        // 画白色圆�?
        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, width / 2, width / 2 - STROKE_WIDTH / 2, paint);
        return output;
    }

    /**
     * 设置圆角图片
     * 
     * @param bitmap：显示的图片
     * @param pixels：圆角的度数
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);

        final float roundPx = pixels;

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }
    
    
    //---------图片加载工具类-----------
    	private static Util util;
    	public static int flag = 0;
    	private Util(){
    		
    	}
    	
    	public static Util getInstance(){
    		if(util == null){
    			util = new Util();
    		}
    		return util;
    	}
    	
    	/**
    	 * 判断是否有sdcard
    	 * @return
    	 */
    	public boolean hasSDCard(){
    		boolean b = false;
    		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
    			b = true;
    		}
    		return b;
    	}
    	
    	/**
    	 * 得到sdcard路径
    	 * @return
    	 */
    	public String getExtPath(){
    		String path = "";
    		if(hasSDCard()){
    			path = Environment.getExternalStorageDirectory().getPath();
    		}
    		return path;
    	}
    	
    	/**
    	 * 得到/data/data/yanbin.imagedownload目录
    	 * @param mActivity
    	 * @return
    	 */
    	public String getPackagePath(Activity mActivity){
    		return mActivity.getFilesDir().toString();
    	}

    	/**
    	 * 根据url得到图片名
    	 * @param url
    	 * @return
    	 */
    	public String getImageName(String url) {
    		String imageName = "";
    		if(url != null){
    			imageName = url.substring(url.lastIndexOf("/") + 1);
    		}
    		return imageName;
    	}

}
