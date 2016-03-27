package com.zyys.zyysdemo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyys.zyysdemo.Api;
import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.bean.RootInfo;

import okhttp3.Call;

public class OnLinePhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private Button hang_up_phone, answer_phone;
    private String mCurrentCallId;
    private static String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_line_phone);
        Intent intent = getIntent();
        String voipAccount = intent.getStringExtra("voipAccount");
        getPhone(voipAccount);

        hang_up_phone = (Button) findViewById(R.id.hang_up_phone);
        answer_phone = (Button) findViewById(R.id.answer_phone);
        hang_up_phone.setOnClickListener(this);
        answer_phone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answer_phone:
                //ECDevice.getECVoIPCallManager().acceptCall(phoneNumber);
                Toast.makeText(this, "正在接听来电！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hang_up_phone:
                //ECDevice.getECVoIPCallManager().releaseCall(phoneNumber);
                ECDevice.getECVoIPCallManager().rejectCall(phoneNumber, 1);
                Toast.makeText(this, "挂断电话", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    public void getPhone(String voipAccount) {
//        String url = "http://120.24.96.138/api/im/phone?voipAccount=" + voipAccount;
        String url = Api.phoneUrl + voipAccount;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        RootInfo rootInfo = new Gson().fromJson(response, RootInfo.class);
                        String phone = rootInfo.getData().getPhone();
                        phoneNumber = phone;//把电话号码设置成全局
                        mCurrentCallId = ECDevice.getECVoIPCallManager().makeCall(ECVoIPCallManager.CallType.DIRECT, phoneNumber);

                    }
                });
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ECDevice.getECVoIPCallManager().rejectCall(phoneNumber, 1);
    }
}
