package forever.foreverandroiddemo.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import forever.foreverandroiddemo.R;

/**
 * Created by forever on 16/6/12.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<MainBean> mBean;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_time, tv_text;
        public RelativeLayout rl_item;

        public ViewHolder(View v) {
            super(v);
            tv_time = (TextView) v.findViewById(R.id.tv_time);
            tv_text = (TextView) v.findViewById(R.id.tv_text);
            rl_item = (RelativeLayout) v.findViewById(R.id.rl_item);
        }
    }

    public MainAdapter(List<MainBean> bean) {
        mBean = bean;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_time.setText(mBean.get(position).getTime());
        holder.tv_text.setText(mBean.get(position).getText());
        holder.rl_item.setTag(position);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, Integer.parseInt(v.getTag().toString()));
            }
        });

    }
    @Override
    public int getItemCount() {
        return mBean.size();
    }
}
