package forever.foreverandroiddemo.videoview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.VideoView;

import forever.foreverandroiddemo.R;


/**
 * 使用VideoView实现
 * @author Administrator
 *
 */
public class VedioPlay2 extends Activity {

	// String path = "file:///sdcard/tencent/MicroMsg/vproxy/e0322k88xsj.2.mp4";

	//	public static String path = "http://218.200.69.66:8302/upload/Media/20150327/43bfda1b-7280-469c-a83b-82fa311c79d7.m4v";
	public static String path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
	// String path = Environment.getExternalStorageDirectory().getPath()
	// + "/k0323q5skv6.2.mp4";
	String pathString=path;
	private final String TAG = "main";

	private Button btn_play, btn_pause, btn_replay, btn_stop;
	private SeekBar seekBar;
	private VideoView vv_video;
	private boolean isPlaying;

	public static void startIntent(Context context) {
		Intent intent = new Intent(context, VedioPlay2.class);
		context.startActivity(intent);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video2);

		seekBar = (SeekBar) findViewById(R.id.seekBar);
		vv_video = (VideoView) findViewById(R.id.vv_videoview);

		btn_play = (Button) findViewById(R.id.btn_play);
		btn_pause = (Button) findViewById(R.id.btn_pause);
		btn_replay = (Button) findViewById(R.id.btn_replay);
		btn_stop = (Button) findViewById(R.id.btn_stop);

		btn_play.setOnClickListener(click);
		btn_pause.setOnClickListener(click);
		btn_replay.setOnClickListener(click);
		btn_stop.setOnClickListener(click);

		// 为进度条添加进度更改事件
		seekBar.setOnSeekBarChangeListener(change);
	}

	private OnSeekBarChangeListener change = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// 当进度条停止修改的时候触发
			// 取得当前进度条的刻度
			int progress = seekBar.getProgress();
			if (vv_video != null && vv_video.isPlaying()) {
				// 设置当前播放的位置
				vv_video.seekTo(progress);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
									  boolean fromUser) {

		}
	};
	private View.OnClickListener click = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				case R.id.btn_play:
					play(0);
					break;
				case R.id.btn_pause:
					pause();
					break;
				case R.id.btn_replay:
					replay();
					break;
				case R.id.btn_stop:
					stop();
					break;
				default:
					break;
			}
		}
	};

	protected void play(int msec) {
		Log.i(TAG, " 获取视频文件地址");
		// String path = et_path.getText().toString().trim();

//		File file = new File(path);
//		if (!file.exists()) {
//			Toast.makeText(this, "视频文件路径错误", 0).show();
//			return;
//		}

		Log.i(TAG, "指定视频源路径");
//		vv_video.setVideoPath(file.getAbsolutePath());
		vv_video.setVideoURI(Uri.parse(pathString));

		Log.i(TAG, "开始播放");
		vv_video.start();

		// 按照初始位置播放
		vv_video.seekTo(msec);
		// 设置进度条的最大进度为视频流的最大播放时长
		seekBar.setMax(vv_video.getDuration());

		// 开始线程，更新进度条的刻度
		new Thread() {

			@Override
			public void run() {
				try {
					isPlaying = true;
					while (isPlaying) {
						// 如果正在播放，没0.5.毫秒更新一次进度条
						int current = vv_video.getCurrentPosition();
						seekBar.setProgress(current);

						sleep(500);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		// 播放之后设置播放按钮不可用
		btn_play.setEnabled(false);

		vv_video.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// 在播放完毕被回调
				btn_play.setEnabled(true);
			}
		});

		vv_video.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// 发生错误重新播放
				play(0);
				isPlaying = false;
				return false;
			}
		});
	}

	/**
	 * 重新开始播放
	 */
	protected void replay() {
		if (vv_video != null && vv_video.isPlaying()) {
			vv_video.seekTo(0);
			Toast.makeText(this, "重新播放", Toast.LENGTH_LONG).show();
			btn_pause.setText("暂停");
			return;
		}
		isPlaying = false;
		play(0);

	}

	/**
	 * 暂停或继续
	 */
	protected void pause() {
		if (btn_pause.getText().toString().trim().equals("继续")) {
			btn_pause.setText("暂停");
			vv_video.start();
			Toast.makeText(this, "继续播放", Toast.LENGTH_LONG).show();
			return;
		}
		if (vv_video != null && vv_video.isPlaying()) {
			vv_video.pause();
			btn_pause.setText("继续");
			Toast.makeText(this, "暂停播放", Toast.LENGTH_LONG).show();
		}
	}

	/*
	 * 停止播放
	 */
	protected void stop() {
		if (vv_video != null && vv_video.isPlaying()) {
			vv_video.stopPlayback();
			btn_play.setEnabled(true);
			isPlaying = false;
		}
	}
}
