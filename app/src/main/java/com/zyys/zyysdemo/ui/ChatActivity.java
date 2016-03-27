package com.zyys.zyysdemo.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;
import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.adapter.MsgAdapter;
import com.zyys.zyysdemo.adb.MySQLiteHelper;
import com.zyys.zyysdemo.bean.Msg;
import com.zyys.zyysdemo.custom.CustomDialogTest;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity {

    private static ListView msgListView;
    private static EditText inputText;
    private Button send;
    private static MsgAdapter msgAdapter;
    private static List<Msg> msgList = new ArrayList<Msg>();
    public static String voipAccount = "";
    private static boolean isFirst = true;
    private MySQLiteHelper helper;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mQueue = Volley.newRequestQueue(ChatActivity.this);
        inputText = (EditText) findViewById(R.id.input_text);//info
        send = (Button) findViewById(R.id.send);//send msg
        msgListView = (ListView) findViewById(R.id.msg_list_view);



        helper = new MySQLiteHelper(ChatActivity.this);

        Intent intent = getIntent();
        voipAccount = intent.getStringExtra("voipAccount");
//        getPhone(voipAccount);//获取电话号码

        //查询数据库对应账号的历史消息
        msgList = helper.selectHistory(voipAccount);
        msgAdapter = new MsgAdapter(msgList, ChatActivity.this);
        msgListView.setAdapter(msgAdapter);
        msgListView.setSelection(msgListView.getBottom());
        isFirst = false;

        /**
         * send msg
         */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputText.getText().toString();//要发送的消息
                try {
                    if (!text.equals("")) {
                        Msg msaage = new Msg(text, Msg.TYPE_SENT);
                        //首先把要发送的消息存进数据库
                        helper.insertHistoryInfo("history", voipAccount, msaage.toString(), Msg.TYPE_SENT);
                        msgList.add(msaage);
                        msgAdapter.notifyDataSetChanged();
                        msgListView.setSelection(msgListView.getBottom());
                        inputText.setText("");
                    }
                    ECMessage msg = ECMessage.createECMessage(ECMessage.Type.TXT);//要发送的消息
                    // 设置消息接收者
                    msg.setTo(voipAccount);
                    // 创建一个文本消息体，并添加到消息对象中
                    ECTextMessageBody msgBody = new ECTextMessageBody(text.toString());
                    // 将消息体存放到ECMessage中
                    msg.setBody(msgBody);
                    // 调用SDK发送接口发送消息到服务器
                    ECChatManager manager = ECDevice.getECChatManager();
                    manager.sendMessage(msg, new ECChatManager.OnSendMessageListener() {
                        @Override
                        public void onProgress(String s, int i, int i1) {
                            // 处理文件发送上传进度（尽上传文件、图片时候SDK回调该方法）
                        }

                        @Override
                        public void onSendMessageComplete(ECError ecError, ECMessage ecMessage) {
                            if (ecMessage == null) {
                                return;
                            }
                            // 将发送的消息更新到本地数据库并刷新UI
                        }
                    });
                } catch (Exception e) {
                    // 处理发送异常
                    Log.e("Exception", "send message fail , e=" + e.getMessage());
                }

            }
        });
    }

    public void sendVoice(View view) {
        Toast.makeText(this, "拨打在线电话", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ChatActivity.this, OnLinePhoneActivity.class);
        intent.putExtra("voipAccount", voipAccount);
        startActivity(intent);
    }


    //和医生聊天的时候，可以直接输入appointment_id就可以增加一条订单
    public void chatOrderBtn(View view) {
        showAlertDialog();//点击下单弹出对话框
    }

    private void showAlertDialog() {//对话框
        final CustomDialogTest dialog = new CustomDialogTest(ChatActivity.this, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = dialog.getCustomView();
        final EditText editText = (EditText) view.findViewById(R.id.et_appointment_id);
        Button positiveButton = (Button) view.findViewById(R.id.positiveButton);
        Button negativeButton = (Button) view.findViewById(R.id.negativeButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appointment_id = editText.getText().toString();
                if (!appointment_id.equals("")) {
                    Intent intent = new Intent(ChatActivity.this, OrderActivity.class);
                    intent.putExtra("appointment_id", appointment_id);
                    intent.putExtra("voipAccount", voipAccount);
                    startActivity(intent);
                    dialog.dismiss();
                } else {
                    Toast.makeText(ChatActivity.this, "appointment_id不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void backChat(View view) {
        finish();
    }

    //接收广播发出的消息
    public static class ChatReceiver extends BroadcastReceiver {
        // private ArrayList<NumberEntity> numberEntityList;
        //List<DrugEntity> drug;
        @Override
        public void onReceive(Context context, Intent intent) {
            String massge = intent.getStringExtra("message");
            String voipAccount = intent.getStringExtra("voipAccount");
            Log.d("123===", massge.toString());
            if (!isFirst) {
                if (massge.indexOf("{") == 0) {
                    Msg msg = new Msg(massge, Msg.TYPE_OTHER);
                    msgList.add(msg);
                    msgAdapter.notifyDataSetChanged();
                } else {
                    Msg msg = new Msg(massge, Msg.TYPE_RECEIVED);
                    msgList.add(msg);
                    msgAdapter.notifyDataSetChanged();
                }

            }

        }
    }
}
