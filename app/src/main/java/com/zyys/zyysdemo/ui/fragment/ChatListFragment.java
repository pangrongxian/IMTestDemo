package com.zyys.zyysdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;
import com.zyys.zyysdemo.Api;
import com.zyys.zyysdemo.MyApplication;
import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.adapter.ChatFiendListAdapter;
import com.zyys.zyysdemo.adb.MySQLiteHelper;
import com.zyys.zyysdemo.bean.Account;
import com.zyys.zyysdemo.bean.Msg;
import com.zyys.zyysdemo.bean.VoipInfo;
import com.zyys.zyysdemo.ui.ChatActivity;
import com.zyys.zyysdemo.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment {

    private ListView lv_chat_friend_list;
    private RequestQueue mQueue;
    private ArrayList<VoipInfo> voipInfoList = new ArrayList<>();
    private ChatFiendListAdapter adapter;
    private MySQLiteHelper helper;
    private List<Msg> msgsList = new ArrayList<>();
    private int num = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_list_fragment, container, false);
        mQueue = Volley.newRequestQueue(getActivity());//初始化队列
        ReceiverData();//接收消息的方法要在数据库判断之前
        lv_chat_friend_list = (ListView) view.findViewById(R.id.lv_chat_friend_list);

        //如果数据库不为空，则从数据库取出数据适配好友列表
        helper = new MySQLiteHelper(getActivity());
        List<Account> accountList = helper.selectAll();
        if (accountList.size() > 0){
            Account account = accountList.get(0);
            String url = Api.avatarUrl + account;
            sendRequest(url);
        }


        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv_chat_friend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击跳转到聊天界面
                //点击获取对方账号，与其进入聊天状态
                // 传递voipAccount到聊天ChatActivity,点击item之前已经存到数据库了
                String voipaccount = helper.selectAll().get(position).getVoipaccount();
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("voipAccount", voipaccount);
                startActivity(intent);
            }
        });
    }

    /**
     * receiver msg
     */
    public void ReceiverData() {
        Log.d("ChatListFragment", "进入消息接收方法");
        ECInitParams params = HttpUtils.getParams();
        params.setOnChatReceiveListener(new OnChatReceiveListener() {
            @Override
            public void OnReceivedMessage(ECMessage msg) {
                String voipAccount = msg.getSessionId();
                Msg message = getMsg(msg); //msg
                msgsList.add(message);

                  MySQLiteHelper sqLiteHelper = new MySQLiteHelper(MyApplication.getContext());
                //把消息存到数据库
                if (message.toString().indexOf("{") == 0){
                    sqLiteHelper.insertHistoryInfo("history", voipAccount,message.toString(),Msg.TYPE_OTHER);
                }else {
                    sqLiteHelper.insertHistoryInfo("history", voipAccount,message.toString(),Msg.TYPE_RECEIVED);
                }
                //发出广播
                Intent intent = new Intent("com.zyys.zyysdemo.ui.INFORMATION");
                intent.putExtra("message", message.toString());
                MyApplication.getContext().sendBroadcast(intent);//完全退出的时候再进到聊天界面，接收消息报空指针

                ArrayList<Account> accountList = ChatListFragment.this.helper.selectAll();//搜索数据库
                //第一个好友发来消息，保存voipAccount到数据库，并且显示到好友列表中
                if (accountList.size() == 0) {//第一次进来数据库肯定为空，先获取好友数据适配一次
                    //把voipAccount存进数据库
                    Account account = new Account(voipAccount);
                    helper.insert("account", account);
                    //调用请求头像和名字的方法
                    String url = Api.avatarUrl + voipAccount;
                    sendRequest(url);
                }
                boolean flag = true;
                //第二个好友进来的时候,判断是否存在过数据库，如果没有存过，把voipAccount保存数据库，并且显示到好友列表
                if (accountList.size() > 0) {//第二次进来就只需判断是否需要存进数据库
                    for (int i = 0; i < accountList.size(); i++) {//第一个好友再次进来,遍历数据库
                        if (voipAccount.equals(accountList.get(i).getVoipaccount().toString())) {//不相同的voipAccount才保存
                            flag = false;//只要循环遍历到相同的就进来
                            break;
                        }
                    }
                    if (flag){
                        Log.d("ChatListFragment", "msgsList:不相同的voipAccount才保存" );
                        Account account = new Account(voipAccount);
                        helper.insert("account", account);
                        //有新的患者发送消息过来
                        String url = Api.avatarUrl + voipAccount;
                        addFriendList(url);//往好友列表上继续添加好友
                        flag = false;
                    }
                }

            }

            @Override
            public void onReceiveMessageNotify(ECMessageNotify ecMessageNotify) {

            }

            @Override
            public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage ecGroupNoticeMessage) {
                // 收到群组通知消息（有人加入、退出...）
                // 可以根据ECGroupNoticeMessage.ECGroupMessageType类型区分不同消息类型
            }

            @Override
            public void onOfflineMessageCount(int i) {
            }

            @Override
            public int onGetOfflineMessage() {
                return 0;
            }

            @Override
            public void onReceiveOfflineMessage(List<ECMessage> list) {
                Log.d("ChatListFragment", "list:" + list);
            }

            @Override
            public void onReceiveOfflineMessageCompletion() {
                // SDK通知应用离线消息拉取完成
            }

            @Override
            public void onServicePersonVersion(int i) {
                // SDK通知应用当前账号的个人信息版本号
            }

            @Override
            public void onReceiveDeskMessage(ECMessage ecMessage) {

            }

            @Override
            public void onSoftVersion(String s, int i) {

            }
        });
    }


    public static Msg getMsg(ECMessage msg) {
        ECMessage.Type type = msg.getType();
        if (type == ECMessage.Type.TXT) {
            // 在这里处理文本消息
            ECTextMessageBody textMessageBody = (ECTextMessageBody) msg.getBody();
            String text = textMessageBody.toString();
            Log.d("textMessageBody===", text);
            String message = textMessageBody.getMessage();
            if (!message.equals("")) {
                Msg msaage = new Msg(message, Msg.TYPE_RECEIVED);
                Log.d("ChatListFragment", message);
                return msaage;
            }
        } else {
            Log.d("ChatListFragment", "不是文本消息");
        }
        return null;
    }

    //get : name img
    public void sendRequest(String url) {
        voipInfoList = new ArrayList<>();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject data = object.getJSONObject("data");
                            String name = data.getString("name");
                            String imgUlr = data.getString("avatar");//头像图片url
                            //set voipInfo data
                            VoipInfo voipInfo = new VoipInfo(imgUlr, name);
                            Log.d("456", "voipInfo:" + voipInfo);
                            helper = new MySQLiteHelper(getActivity());
                            List<Account> accountList = helper.selectAll();

                            if (num < accountList.size()-1) {
                                num++;
                                String url = Api.avatarUrl + accountList.get(num);
                                Log.d("123", "url=" + url);
                                sendRequest(url);//为什么适配上去顺序不对
                            }
                            voipInfoList.add(voipInfo);
                            if(num == accountList.size()-1){//添加完数据之后再适配
                                adapter = new ChatFiendListAdapter(getActivity(), voipInfoList);
                                lv_chat_friend_list.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        mQueue.add(request);
    }

    //新的好友发送消息过来，把他继续添加到好友列表
    //get : name img
    public void addFriendList(String url) {
        voipInfoList = new ArrayList<>();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject data = object.getJSONObject("data");
                            String name = data.getString("name");
                            String imgUlr = data.getString("avatar");//头像图片url
                            //set voipInfo data
                            VoipInfo voipInfo = new VoipInfo(imgUlr, name);
                            voipInfoList.add(voipInfo);
                            adapter.addData(voipInfoList);//往好友列表上接续添加好友
                            adapter.notifyDataSetChanged();

//                            adapter = new ChatFiendListAdapter(getActivity(),voipInfoList);
//                            lv_chat_friend_list.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        mQueue.add(request);
    }

}
