package forever.foreverandroiddemo.videoview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import forever.foreverandroiddemo.R;


/**
 * 使用MediaController结合VideoView实现
 *
 * @author Administrator
 *
 */
public class VedioPlay3 extends Activity {

	// String path = "file:///sdcard/tencent/MicroMsg/vproxy/e0322k88xsj.2.mp4";

	//	public static String path = "http://218.200.69.66:8302/upload/Media/20150327/43bfda1b-7280-469c-a83b-82fa311c79d7.m4v";
	public static String path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
	// String path = Environment.getExternalStorageDirectory().getPath()
	// + "/k0323q5skv6.2.mp4";
	String pathString = path;

	private VideoView vv_video;
	private MediaController mController;

	public static void startIntent(Context context) {
		Intent intent = new Intent(context, VedioPlay3.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video3);
		vv_video = (VideoView) findViewById(R.id.vv_video);
		// 实例化MediaController
		mController = new MediaController(this);
		//
		// File file = new File(pathString);
		// if (file.exists()) {
		// 设置播放视频源的路径
		// vv_video.setVideoPath(file.getAbsolutePath());

		vv_video.setVideoURI(Uri.parse(pathString));

		// 为VideoView指定MediaController
		vv_video.setMediaController(mController);
		// 为MediaController指定控制的VideoView
		mController.setMediaPlayer(vv_video);
		// 增加监听上一个和下一个的切换事件，默认这两个按钮是不显示的
		mController.setPrevNextListeners(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(VedioPlay3.this, "下一个", Toast.LENGTH_LONG).show();
			}
		}, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(VedioPlay3.this, "上一个", Toast.LENGTH_LONG).show();
			}
		});
		// }
	}
}
