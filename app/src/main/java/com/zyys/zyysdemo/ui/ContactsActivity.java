package com.zyys.zyysdemo.ui;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.ui.fragment.ChatListFragment;
import com.zyys.zyysdemo.ui.fragment.OrderFragment;

public class ContactsActivity extends AppCompatActivity {

    private Button[] mTabs;
    private FragmentManager manager;
    private int currentTabIndex;
    private int index;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        init();//初始化
        mTabs = new Button[2];
        mTabs[0] = (Button) findViewById(R.id.btn_conversation);
        mTabs[1] = (Button) findViewById(R.id.btn_chat_order);

        mTabs[0].setTextColor(Color.WHITE);
        mTabs[0].setBackgroundColor(Color.parseColor("#FFE5E5"));
        mTabs[1].setTextColor(Color.parseColor("#333333"));
        mTabs[1].setBackgroundColor(Color.parseColor("#D4D4D4"));

    }

    //初始化布局和fragment
    private void init() {
        manager =getSupportFragmentManager();
        ChatListFragment chatListFragment = new ChatListFragment();
        OrderFragment orderFragment = new OrderFragment();
        fragments = new Fragment[]{chatListFragment,orderFragment};
        manager.beginTransaction().add(R.id.ll_contacts,chatListFragment).show(chatListFragment).commit();
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_conversation:
                index = 0;
                mTabs[0].setTextColor(Color.WHITE);
                mTabs[0].setBackgroundColor(Color.parseColor("#FFE5E5"));
                mTabs[1].setTextColor(Color.parseColor("#333333"));
                mTabs[1].setBackgroundColor(Color.parseColor("#D4D4D4"));
                break;
            case R.id.btn_chat_order:
                index = 1;
                mTabs[1].setTextColor(Color.WHITE);
                mTabs[1].setBackgroundColor(Color.parseColor("#FFE5E5"));
                mTabs[0].setTextColor(Color.parseColor("#333333"));
                mTabs[0].setBackgroundColor(Color.parseColor("#D4D4D4"));
                break;
        }
        if (currentTabIndex!=index){
            FragmentTransaction ft=manager.beginTransaction();
            ft.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()){
                ft.add(R.id.ll_contacts,fragments[index]);
            }
            ft.show(fragments[index]).commit();
        }
        currentTabIndex=index;
    }

}
