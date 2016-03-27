package com.zyys.zyysdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.bean.OrderDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/27.
 */
public class CheckOrderAdapter extends BaseAdapter{

    private Context context;
    private List<OrderDetail> orderDetails = new ArrayList<>();
    private LayoutInflater inflater;

    public CheckOrderAdapter(Context context, List<OrderDetail> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return orderDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return orderDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.check_order_item,null);
            holder.tv_order = (TextView) convertView.findViewById(R.id.tv_order);
            holder.tv_patient = (TextView) convertView.findViewById(R.id.tv_patient);
            holder.tv_doctor = (TextView) convertView.findViewById(R.id.tv_doctor);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.has_pay = (TextView) convertView.findViewById(R.id.has_pay);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        int num = orderDetails.get(position).getNum();
        holder.tv_order.setText(num + "");
        holder.tv_patient.setText(orderDetails.get(position).getName());
        holder.tv_doctor.setText(orderDetails.get(position).getCreated_at());
        holder.tv_date.setText(orderDetails.get(position).getDoctor_name());

        int has_pay = orderDetails.get(position).getHas_pay();
        Log.d("has_pay", "has_pay:" + has_pay);
        if (has_pay == 1){
            holder.has_pay.setText("已付款");
            holder.has_pay.setTextColor(Color.parseColor("#DD4E42"));
        }else {
            holder.has_pay.setText("未付款");
            holder.has_pay.setTextColor(Color.parseColor("#DD4E42"));
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv_order,tv_patient,tv_doctor,tv_date,has_pay;
    }
}
