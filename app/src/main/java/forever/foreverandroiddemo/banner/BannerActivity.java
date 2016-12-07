package forever.foreverandroiddemo.banner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import forever.foreverandroiddemo.R;
import forever.foreverandroiddemo.banner.holder.BannerHolderCreator;
import forever.foreverandroiddemo.banner.listener.OnItemClickListener;
import forever.foreverandroiddemo.banner.view.BannerView;

/**
 * Created by forever on 16/7/8.
 */
public class BannerActivity extends AppCompatActivity implements OnItemClickListener {

    BannerView bannerView;
    private BannerHolderCreator<BannerImageHolder> holder;
    private List<String> data = new ArrayList<>();
    private String []urls=new String[]{"http://img4.imgtn.bdimg.com/it/u=852584205,227742241&amp;fm=21&amp;gp=0.jpg",
            "http://pic.58pic.com/58pic/12/36/16/42n58PICrk9.jpg","http://img2.3lian.com/2014/f5/147/d/36.jpg"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();
    }

    public void initView() {
//        urls = getResources().getStringArray(R.array.banner_three);
        Collections.addAll(data, urls);
        holder = new BannerHolderCreator<BannerImageHolder>() {
            @Override
            public BannerImageHolder createHolder() {
                return new BannerImageHolder();
            }
        };
        bannerView= (BannerView) findViewById(R.id.banner);
        bannerView.setPages(holder, data);
        bannerView.setPoint(R.mipmap.home_banner_selected, R.mipmap.home_banner_normal);
        bannerView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bannerView.start(2000);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(BannerActivity.this,"",Toast.LENGTH_LONG).show();
    }
}
