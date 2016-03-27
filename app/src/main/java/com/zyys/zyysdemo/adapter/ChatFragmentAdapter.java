package com.zyys.zyysdemo.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.bean.ContractInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 */
public class ChatFragmentAdapter extends BaseAdapter{

    private Context context;
    private List<ContractInfo> contractInfoList;
    private LayoutInflater inflater;

    public ChatFragmentAdapter(Context context, List<ContractInfo> contractInfoList) {
        this.context = context;
        this.contractInfoList = contractInfoList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contractInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return contractInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.chat_fragment_item,null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(contractInfoList.get(position).getName());
        holder.phone.setText(contractInfoList.get(position).getPhoneNumber());
        holder.iv_head.setImageResource(R.mipmap.ic_launcher);
        return convertView;
    }

    class ViewHolder{
        ImageView iv_head;
        TextView name,phone;
    }
}
