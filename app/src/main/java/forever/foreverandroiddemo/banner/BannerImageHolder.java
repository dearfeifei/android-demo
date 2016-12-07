package forever.foreverandroiddemo.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import forever.foreverandroiddemo.R;
import forever.foreverandroiddemo.banner.holder.Holder;
import forever.foreverandroiddemo.utils.GlideUtils;


/**
 * 说明：BannerImageHolder
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/12 21:44
 * <p/>
 * 版本：verson 1.0
 */
public class BannerImageHolder implements Holder<String> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void convert(Context context, int position, String item) {
        GlideUtils.displayUrl(imageView, item, R.mipmap.ic_launcher);
    }


}
