package forever.foreverandroiddemo.main;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import forever.foreverandroiddemo.banner.BannerActivity;
import forever.foreverandroiddemo.R;
import forever.foreverandroiddemo.surfaceview.VedioPlay1;
import forever.foreverandroiddemo.surfaceview.VedioPlay4;
import forever.foreverandroiddemo.videoview.VedioPlay2;
import forever.foreverandroiddemo.videoview.VedioPlay3;

public class MainActivity extends AppCompatActivity {
    List<MainBean> listDate = new ArrayList<>();
    MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDate();

    }

    public void initView() {
        //监听下拉刷新
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新刷新页面
                final Timer mTimer = new Timer();
                TimerTask mTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //隐藏进度
                                swipeLayout.setRefreshing(false);
                            }
                        });

                    }
                };
                //开始一个定时任务，1000毫秒后执行
                mTimer.schedule(mTimerTask, 1000);
            }
        });


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MainAdapter(listDate);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(listener);

    }


    //条目点击事件监听
    MainAdapter.OnRecyclerViewItemClickListener listener = new MainAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int data) {
            startActivity(new Intent(MainActivity.this, listDate.get(data).getClazz()));
        }
    };

    public void initDate() {
        listDate.add(new MainBean("2016年7月8日 周五 19:15","Android轮播图，引导页",BannerActivity.class));
        listDate.add(new MainBean("2016年9月12日 周一 10:26","VideoView播放视频",VedioPlay2.class));
        listDate.add(new MainBean("2016年9月12日 周一 10:26","VideoView+MediaController播放视频",VedioPlay3.class));
        listDate.add(new MainBean("2016年9月12日 周一 10:26","SurfaceView+MediaPlayer播放视频",VedioPlay1.class));
        listDate.add(new MainBean("2016年9月12日 周一 10:26","SurfaceView+MediaPlayer播放视频",VedioPlay4.class));

        mAdapter.notifyDataSetChanged();
    }

}
