package forever.foreverandroiddemo.surfaceview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

import forever.foreverandroiddemo.R;


public class VedioPlay1 extends Activity {

	public static void startIntent(Context context) {
		Intent intent = new Intent(context, VedioPlay1.class);
		context.startActivity(intent);
	}


	// String path = "file:///sdcard/tencent/MicroMsg/vproxy/e0322k88xsj.2.mp4";

	//	public static String path = "http://218.200.69.66:8302/upload/Media/20150327/43bfda1b-7280-469c-a83b-82fa311c79d7.m4v";
	public static String path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
	// String path = Environment.getExternalStorageDirectory().getPath()
	// + "/k0323q5skv6.2.mp4";
	private SurfaceView surfaceView;
	private Button btnPause, btnPlayUrl, btnStop;
	private SeekBar skbProgress;
	private Player player;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video1);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView1);

		btnPlayUrl = (Button) this.findViewById(R.id.btnPlayUrl);
		btnPlayUrl.setOnClickListener(new ClickEvent());

		btnPause = (Button) this.findViewById(R.id.btnPause);
		btnPause.setOnClickListener(new ClickEvent());

		btnStop = (Button) this.findViewById(R.id.btnStop);
		btnStop.setOnClickListener(new ClickEvent());

		skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
		skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		player = new Player(surfaceView, skbProgress);
	}

	class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (arg0 == btnPause) {
				player.pause();
			} else if (arg0 == btnPlayUrl) {
//				String url = "http://daily3gp.com/vids/family_guy_penis_car.3gp";
				String url = path;
				player.playUrl(url);
			} else if (arg0 == btnStop) {
				player.stop();
			}

		}
	}

	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		int progress;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
									  boolean fromUser) {
			// 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
			this.progress = progress * player.mediaPlayer.getDuration()
					/ seekBar.getMax();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
			player.mediaPlayer.seekTo(progress);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		player.clear();
		player.stop();
		surfaceView=null;
	}
}