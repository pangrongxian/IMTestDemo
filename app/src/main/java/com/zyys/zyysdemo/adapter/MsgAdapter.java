package com.zyys.zyysdemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.bean.Msg;
import com.zyys.zyysdemo.bean.medicine_oeder.DrugEntity;
import com.zyys.zyysdemo.bean.medicine_oeder.NumberEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MsgAdapter extends BaseAdapter {

    private List<Msg> msgList = new ArrayList<Msg>();
    private Context context;
    private LayoutInflater inflater;
    private boolean flag;
    public MsgAdapter(List<Msg> msgList, Context context) {
        this.msgList = msgList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public MsgAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    /**
     * 数据改变的时候调用此方法（可是没有效果？？？）
     *
     * @param messageList
     */
    public void flashData(List<Msg> messageList) {
//        msgList.clear();
//        msgList.addAll(messageList);
//        this.msgList = messageList;
//        notifyDataSetChanged();
    }

    public void clearData(List<Msg> messageList) {
        messageList.clear();
    }


    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        Msg m = msgList.get(position);
        int viewType = m.getType();
        //数据源

        if (viewType == Msg.TYPE_RECEIVED) {//0
            convertView = inflater.inflate(R.layout.chat_listitem_left, null);
            holder.tv_item_msg = (TextView) convertView.findViewById(R.id.tv_item_msg);
            holder.tv_item_msg.setText((m.getContent()).toString());
        } else if (viewType == Msg.TYPE_SENT) {//1
            convertView = inflater.inflate(R.layout.chat_listitem_right, null);
            holder.tv_item_msg = (TextView) convertView.findViewById(R.id.tv_item_msg);
            holder.tv_item_msg.setText((m.getContent()).toString());
        } else if (viewType == Msg.TYPE_OTHER) {
            convertView = inflater.inflate(R.layout.medicine_order_item, null);
            holder.record_name = (TextView) convertView.findViewById(R.id.record_name);
            holder.relation = (TextView) convertView.findViewById(R.id.relation);
            holder.patient_name = (TextView) convertView.findViewById(R.id.patient_name);
            holder.book_time = (TextView) convertView.findViewById(R.id.book_time);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.appointment_id = (TextView) convertView.findViewById(R.id.appointment_id);
            holder.ll_add_view = (LinearLayout) convertView.findViewById(R.id.ll_add_view);
            //Msg medicineMsg = new Msg(record_name,relation,patient_name,book_time,type,drug,Msg.TYPE_OTHER);
            if (m.getContent().indexOf("{") == 0) {
                flag = true;
                try {
                    JSONObject jsonObject = new JSONObject(m.getContent());
                    JSONObject object = jsonObject.getJSONObject("appointment_info");
                    JSONArray jsonArray = jsonObject.getJSONArray("drug");

                    holder.record_name.setText(object.getString("record_name"));
                    holder.relation.setText( object.getString("relation"));
                    holder.patient_name.setText(object.getString("patient_name"));
                    holder.book_time.setText(object.getString("book_time"));
                    holder.type.setText( object.getString("type"));
                    holder.appointment_id.setText( object.getString("appointment_id"));

                    ArrayList<NumberEntity> numberEntityList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String interval = jsonArray.getJSONObject(i).getString("interval");
                        String mediaclName = jsonArray.getJSONObject(i).getString("mediaclName");
                        String unit = jsonArray.getJSONObject(i).getString("unit");//单位
                        Log.d("MsgAdapter", "unit:" + unit);

                        JSONArray numbers = jsonArray.getJSONObject(i).getJSONArray("numbers");

//                        if (flag){
//                            for (int j = 0; j < jsonArray.length(); j++) {

                                String morning = numbers.getJSONObject(0).getString("早");
                                String afternoom = numbers.getJSONObject(1).getString("午");
                                String evening = numbers.getJSONObject(2).getString("晚");
                                String sleep = numbers.getJSONObject(3).getString("睡前");

                                if (morning.equals("")){
                                    morning = "0";
                                }
                                if (afternoom.equals("")){
                                    afternoom = "0";
                                }
                                if (evening.equals("")){
                                    evening = "0";
                                }
                                if (sleep.equals("")){
                                    sleep = "0";
                                }
                                NumberEntity numberEntity = new NumberEntity(morning, afternoom, evening, sleep);
                                //numberEntityList = new ArrayList<>();
                                numberEntityList.add(numberEntity);
//                            }
//                            Log.d("MsgAdapter", "numberEntityList.size():" + numberEntityList.size());
//                            Log.d("MsgAdapter", "numberEntityList:" + numberEntityList.toString());
//                            flag = false;
//                        }
                        View llt_msg = inflater.inflate(R.layout.ll_add_view_item, null);
                        TextView textView1 = (TextView) llt_msg.findViewById(R.id.interval);
                        TextView textView2 = (TextView) llt_msg.findViewById(R.id.mediaclName);

                        TextView tv_m = (TextView) llt_msg.findViewById(R.id.morning);
                        TextView tv_a = (TextView) llt_msg.findViewById(R.id.afternoom);
                        TextView tv_e = (TextView) llt_msg.findViewById(R.id.evening);
                        TextView tv_s = (TextView) llt_msg.findViewById(R.id.sleep);

                        TextView unit1 = (TextView) llt_msg.findViewById(R.id.unit1);
                        TextView unit2 = (TextView) llt_msg.findViewById(R.id.unit2);
                        TextView unit3 = (TextView) llt_msg.findViewById(R.id.unit3);
                        TextView unit4 = (TextView) llt_msg.findViewById(R.id.unit4);

                        textView1.setText(interval);
                        textView2.setText(mediaclName);
//                        for (int k = 0; k < jsonArray.length(); k++) {
                            Log.d("MsgAdapter", "jsonArray.length():" + jsonArray.length());
                            tv_m.setText(numberEntityList.get(i).getMoning().toString());
                            tv_a.setText(numberEntityList.get(i).getAfternoom().toString());
                            tv_e.setText(numberEntityList.get(i).getEvening().toString());
                            tv_s.setText(numberEntityList.get(i).getSleep().toString());

                            unit1.setText(unit.toString());
                            unit2.setText(unit.toString());
                            unit3.setText(unit.toString());
                            unit4.setText(unit.toString());
//                        }
                        holder.ll_add_view.addView(llt_msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView record_name, tv_item_msg, relation, patient_name, book_time, type,appointment_id;
        LinearLayout ll_add_view;
    }
}
