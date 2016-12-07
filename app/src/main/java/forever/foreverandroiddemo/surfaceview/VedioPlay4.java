package forever.foreverandroiddemo.surfaceview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import forever.foreverandroiddemo.R;


/**
 * 使用MediaPlayer结合SurfaceCView实现
 *
 * @author Administrator
 *
 */
public class VedioPlay4 extends Activity {

	public static void startIntent(Context context) {
		Intent intent = new Intent(context, VedioPlay4.class);
		context.startActivity(intent);
	}


	// String path = "file:///sdcard/tencent/MicroMsg/vproxy/e0322k88xsj.2.mp4";

	//	public static String path = "http://218.200.69.66:8302/upload/Media/20150327/43bfda1b-7280-469c-a83b-82fa311c79d7.m4v";
	public static String path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
	// String path = Environment.getExternalStorageDirectory().getPath()
	// + "/k0323q5skv6.2.mp4";
	String pathString = path;
	private final String TAG = "main";

	private SurfaceView sv;
	private Button btn_play, btn_pause, btn_replay, btn_stop;
	private MediaPlayer mediaPlayer;
	private SeekBar seekBar;
	private int currentPosition = 0;
	private boolean isPlaying;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video4);

		seekBar = (SeekBar) findViewById(R.id.seekBar);
		sv = (SurfaceView) findViewById(R.id.sv);

		btn_play = (Button) findViewById(R.id.btn_play);
		btn_pause = (Button) findViewById(R.id.btn_pause);
		btn_replay = (Button) findViewById(R.id.btn_replay);
		btn_stop = (Button) findViewById(R.id.btn_stop);

		btn_play.setOnClickListener(click);
		btn_pause.setOnClickListener(click);
		btn_replay.setOnClickListener(click);
		btn_stop.setOnClickListener(click);

		// 为SurfaceHolder添加回调
		sv.getHolder().addCallback(callback);

		// 4.0版本之下需要设置的属性
		// 设置Surface不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到界面
		// sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		// 为进度条添加进度更改事件
		seekBar.setOnSeekBarChangeListener(change);
	}

	private Callback callback = new Callback() {
		// SurfaceHolder被修改的时候回调
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.i(TAG, "SurfaceHolder 被销毁");
			// 销毁SurfaceHolder的时候记录当前的播放位置并停止播放
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				currentPosition = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
			}
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Log.i(TAG, "SurfaceHolder 被创建");
			if (currentPosition > 0) {
				// 创建SurfaceHolder的时候，如果存在上次播放的位置，则按照上次播放位置进行播放
				play(currentPosition);
				currentPosition = 0;
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
								   int height) {
			Log.i(TAG, "SurfaceHolder 大小被改变");
		}

	};

	private OnSeekBarChangeListener change = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// 当进度条停止修改的时候触发
			// 取得当前进度条的刻度
			int progress = seekBar.getProgress();
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				// 设置当前播放的位置
				mediaPlayer.seekTo(progress);
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

	/*
	 * 停止播放
	 */
	protected void stop() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			btn_play.setEnabled(true);
			isPlaying = false;
		}
	}

	/**
	 * 开始播放
	 *
	 * @param msec
	 *            播放初始位置
	 *
	 */
	protected void play(final int msec) {
		// 获取视频文件地址
		// String path = et_path.getText().toString().trim();

		// File file = new File(pathString);
		// if (!file.exists()) {
		// Toast.makeText(this, "视频文件路径错误", 0).show();
		// return;
		// }
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			// 设置播放的视频源
			// mediaPlayer.setDataSource(file.getAbsolutePath());
			mediaPlayer.setDataSource(pathString);

			// 设置显示视频的SurfaceHolder
			mediaPlayer.setDisplay(sv.getHolder());
			Log.i(TAG, "开始装载");
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					Log.i(TAG, "装载完成");
					mediaPlayer.start();
					// 按照初始位置播放
					mediaPlayer.seekTo(msec);
					// 设置进度条的最大进度为视频流的最大播放时长
					seekBar.setMax(mediaPlayer.getDuration());
					// 开始线程，更新进度条的刻度
					new Thread() {

						@Override
						public void run() {
							try {
								isPlaying = true;
								while (isPlaying) {
									int current = mediaPlayer
											.getCurrentPosition();
									seekBar.setProgress(current);

									sleep(500);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}.start();

					btn_play.setEnabled(false);
				}
			});
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// 在播放完毕被回调
					btn_play.setEnabled(true);
				}
			});

			mediaPlayer.setOnErrorListener(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// 发生错误重新播放
					play(0);
					isPlaying = false;
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 重新开始播放
	 */
	protected void replay() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.seekTo(0);
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
			mediaPlayer.start();
			Toast.makeText(this, "继续播放", Toast.LENGTH_LONG).show();
			return;
		}
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			btn_pause.setText("继续");
			Toast.makeText(this, "暂停播放", Toast.LENGTH_LONG).show();
		}

	}

}
