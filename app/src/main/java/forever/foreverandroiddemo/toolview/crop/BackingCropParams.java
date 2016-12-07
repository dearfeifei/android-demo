package forever.foreverandroiddemo.toolview.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created with Android Studio.
 * User: ryan@xisue.com
 * Date: 10/3/14
 * Time: 11:12 AM
 * Desc: CropParams
 * Revision:
 * - 11:00 2014/10/03 Encapsulate the crop params.
 * - 13:20 2014/10/03 Put the initialization into constructor method.
 * - 14:00 2014/10/03 Make the crop as String instead of Boolean.
 * - 14:30 2014/10/03 Increase the default output size from 200 to 300.
 * - 12:20 2014/10/04 Add "scaleUpIfNeeded" crop options for scaling up cropped images if the size is too small.
 * - 23:10 2015/08/20 Add enable to toggle on/off the crop function.
 * - 13:30 2015/08/21 Add compress options to avoid OOM or other problems.
 * - 23:00 2015/09/05 Add default compress width & height.
 */
public class BackingCropParams {

    public static final String CROP_TYPE = "image/*";
    public static final String OUTPUT_FORMAT = Bitmap.CompressFormat.JPEG.toString();

    public static final int DEFAULT_ASPECTX = 16;
    public static final int DEFAULT_ASPECTY = 9;
    public static final int DEFAULT_OUTPUTX = 600;
    public static final int DEFAULT_OUTPUTY = 300;
    public static final int DEFAULT_COMPRESS_WIDTH = 1080;
    public static final int DEFAULT_COMPRESS_HEIGHT = 540;
    public static final int DEFAULT_COMPRESS_QUALITY = 1200;

    public Uri uri;

    public String type;
    public String outputFormat;
    public String crop;

    public boolean scale;
    public boolean returnData;
    public boolean noFaceDetection;
    public boolean scaleUpIfNeeded;

    /**
     * Default is true, if set false, crop function will not work,
     * it will only pick up images from gallery or take pictures from camera.
     */
    public boolean enable;

    /**
     * Default is false, if it is from capture and without crop, the image could be large
     * enough to trigger OOM, it is better to compress image while enable is false
     */
    public boolean compress;

    public boolean rotateToCorrectDirection;

    public int compressWidth;
    public int compressHeight;
    public int compressQuality;

    public int aspectX;
    public int aspectY;

    public int outputX;
    public int outputY;

    public Context context;

    public BackingCropParams(Context context) {
        this.context = context;
        type = CROP_TYPE;
        outputFormat = OUTPUT_FORMAT;
        crop = "true";
        scale = true;
        returnData = false;
        noFaceDetection = true;
        scaleUpIfNeeded = true;
        enable = true;
        rotateToCorrectDirection = false;
        compress = false;
        compressQuality = DEFAULT_COMPRESS_QUALITY;
        compressWidth = DEFAULT_COMPRESS_WIDTH;
        compressHeight = DEFAULT_COMPRESS_HEIGHT;
        aspectX =DEFAULT_ASPECTX;
        aspectY =DEFAULT_ASPECTY;
        outputX =DEFAULT_OUTPUTX;
        outputY =DEFAULT_OUTPUTY;
        refreshUri();
    }

    public void refreshUri() {
        uri = BackingCropHelper.generateUri();
    }
}
