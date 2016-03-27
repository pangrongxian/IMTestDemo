package com.zyys.zyysdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.bean.VoipInfo;
import com.zyys.zyysdemo.utils.MemoryCache;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/3/3.
 */
public class ChatFiendListAdapter extends BaseAdapter{

    private Context context;
    private List<VoipInfo> voipInfoList;
    private LayoutInflater inflater;
    private RequestQueue mQueue;
    private ImageLoader loader;


    public ChatFiendListAdapter(Context context, List<VoipInfo> voipInfoList) {
        this.context = context;
        this.voipInfoList = voipInfoList;
        inflater = LayoutInflater.from(context);
        mQueue = Volley.newRequestQueue(context);
    }

    public ChatFiendListAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<VoipInfo> infoList){
//        voipInfoList.clear();
        voipInfoList.addAll(infoList);
    }


    @Override
    public int getCount() {
        return voipInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return voipInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.friend_list_item,null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.friend_list_name);
            holder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.iv_friend_list);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        loader = new ImageLoader(mQueue,new MemoryCache());
        //设置图片加载的url和图片加载器
        String imgUrl = voipInfoList.get(position).getImgUrl();

        if (imgUrl != null){
            holder.networkImageView.setImageUrl(imgUrl,loader);
            holder.name.setText(voipInfoList.get(position).getName());
        }else {
            holder.networkImageView.setImageResource(R.mipmap.ic_launcher);//先设置默认图片
        }
        return convertView;
    }

    public class ViewHolder{
        NetworkImageView networkImageView;
        TextView name;
    }
}
