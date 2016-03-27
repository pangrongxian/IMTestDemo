package com.zyys.zyysdemo.ui.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.adapter.ChatFragmentAdapter;
import com.zyys.zyysdemo.bean.ContractInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/3/2.
 */
public class ChatFragment extends Fragment {

    private ListView lv_chat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.chat_fragment,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv_chat = (ListView)view.findViewById(R.id.lv_chat);
        List<ContractInfo> contractInfoList = getPhoneContracts(getContext());

        ChatFragmentAdapter adapter = new ChatFragmentAdapter(getActivity(),contractInfoList);
        lv_chat.setAdapter(adapter);

        lv_chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ChatFragment", "position:" + position);
            }
        });
    }

    //取本机通讯录
    public static List<ContractInfo> getPhoneContracts(Context mContext){
        List<ContractInfo> list =new ArrayList<>();
        ContentResolver resolver = mContext.getContentResolver();
// 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, null, null, null); //传入正确的uri
        if(phoneCursor!=null){
            while(phoneCursor.moveToNext()){
                int nameIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME); //获取联系人name
                String name = phoneCursor.getString(nameIndex);
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)); //获取联系人number
                if(TextUtils.isEmpty(phoneNumber)){
                    continue;
                }
//以下是我自己的数据封装。
                ContractInfo contractInfo = new ContractInfo();
                contractInfo.setName(name);
                contractInfo.setPhoneNumber(phoneNumber);
                list.add(contractInfo);
            }
            phoneCursor.close();
        }
        return list;
    }


   // 接下来看获取sim卡的方法，sim卡的uri有两种可能content://icc/adn与content://sim/adn （一般情况下是第一种）
    public static HashMap<String, ContractInfo> getSimContracts(Context mContext){
//读取SIM卡手机号,有两种可能:content://icc/adn与content://sim/adn
        HashMap<String, ContractInfo> map = new HashMap<String, ContractInfo>();

        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri,null, null, null, null);
        if(phoneCursor!=null){
            while(phoneCursor.moveToNext()){
                String name = phoneCursor.getString(phoneCursor.getColumnIndex("name"));
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex("number"));
                if(TextUtils.isEmpty(phoneNumber)){
                    continue;
                }
//以下是我自己的数据封装。
                ContractInfo contractInfo = new ContractInfo();
                contractInfo.setName(name);
                contractInfo.setPhoneNumber(phoneNumber);
                map.put(phoneNumber, contractInfo);
            }
            phoneCursor.close();
        }
        return map;
    }

}
