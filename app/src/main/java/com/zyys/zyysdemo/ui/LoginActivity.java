package com.zyys.zyysdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.zyys.zyysdemo.Api;
import com.zyys.zyysdemo.MyApplication;
import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.utils.DataUtil;
import com.zyys.zyysdemo.utils.HttpUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username,et_password;
    private RequestQueue mQueue ;
    private CheckBox cb_rememberpwd;
    private DataUtil util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        cb_rememberpwd = (CheckBox) findViewById(R.id.cb_rememberpwd);
        util = new DataUtil(this);

        Map<String, ?> map = util.getData("user");
        Object name = map.get("userName");
        if (name != null && !name.equals("")) {
            et_username.setText((String) name);
        }
        Object password = map.get("userPassword");
        if (password != null && !password.equals("")) {
            et_password.setText((String) password);
        }
        Object isPassword = map.get("isPassword");
        if (isPassword != null && !isPassword.equals("")) {
            cb_rememberpwd.setChecked((Boolean) isPassword);
        }
    }

    public void login(View view) {
        final String username = et_username.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        if (username.equals("") || username == null) {
            Toast.makeText(getApplicationContext(), "用户名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (password.equals("") || password == null) {
            Toast.makeText(getApplicationContext(), "密码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (username.equals("drug") && password.equals("test")){
            getLoginData(username, password);
            if (cb_rememberpwd.isChecked()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userName", username);
                map.put("userPassword", password);
                map.put("isPassword", cb_rememberpwd.isChecked());
                util.putData("user", map);
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userName", "");
                map.put("userPassword", "");
                map.put("isPassword", false);
                util.putData("user", map);
            }
        }
        startActivity(new Intent(LoginActivity.this, ContactsActivity.class));
    }

    //返回登录返回数据
    public void getLoginData(final String name, final String password){//获取登录消息（成功获取）
//        String url = "http://api.zhaoyang120.cn/api/drug/login";
        String url = Api.loginUrl;
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginActivity.this, "获取用户信息成功！", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject object = new JSONObject(response);

                            JSONObject data = object.getJSONObject("data").getJSONObject("account");
                            String user_id = data.getString("user_id");
                            String subAccountSid = data.getString("subAccountSid");
                            String subToken = data.getString("subToken");
                            String voipAccount = data.getString("voipAccount");
                            String voipPwd = data.getString("voipPwd");

                            //come back login info
                            init(LoginActivity.this, voipAccount, voipPwd);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "获取用户信息失败", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("name",name);
                param.put("password",password);
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        mQueue.add(request);
    }

    // 第一步：初始化SDK
    public void init(final Context context, final String voipAccount , final String voipPassword ) {
        if (!ECDevice.isInitialized()) {
            ECDevice.initial(context, new ECDevice.InitListener() {
                @Override
                public void onInitialized() {
                    // SDK已经初始化成功
                    initParams(voipAccount, voipPassword);
                }

                @Override
                public void onError(Exception exception) {
                    Toast.makeText(context, "初始化失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    //第二步：设置注册参数、设置通知回调监听
    public static void initParams(String voipAccount ,String voidPassword) {
        // 构建注册所需要的参数信息
        ECInitParams params = HttpUtils.getParams();
        params.setUserid(voipAccount);
        params.setPwd(voidPassword);
        //测试
//        params.setAppKey("aaf98f89506fc2f001507e1cab440ace");
//        params.setToken("a8d27b4500d3b3dcfbf96296091abf4d");
        //正式
        params.setAppKey("aaf98f894ee24b5f014ee24edc970000");
        params.setToken("7c54a355857a08b2dfc3769c7dc05e8a");

        //RONGLIAN_APPID=aaf98f894ee24b5f014ee24edc970000
        //RONGLIAN_APPTOKEN=7c54a355857a08b2dfc3769c7dc05e8a


//        // 设置登陆验证模式（是否验证密码）PASSWORD_AUTH-密码登录方式
        params.setAuthType(ECInitParams.LoginAuthType.PASSWORD_AUTH);
        params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);

        params.setOnDeviceConnectListener(new ECDevice.OnECDeviceConnectListener() {
            @Override
            public void onConnect() {
            }

            @Override
            public void onDisconnect(ECError ecError) {
            }

            @Override
            public void onConnectState(ECDevice.ECConnectState state, ECError error) {
                Log.d("error=",error.toString());
                if (state == ECDevice.ECConnectState.CONNECT_FAILED) {
                    if (error.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
                        //账号异地登陆
                        Toast.makeText(MyApplication.getContext(), "账号异地登陆", Toast.LENGTH_SHORT).show();
                    } else {
                        //连接状态失败
                        Toast.makeText(MyApplication.getContext(), "连接状态失败,请重新登录！", Toast.LENGTH_SHORT).show();
                    }
                    return;
                } else if (state == ECDevice.ECConnectState.CONNECT_SUCCESS) {
                    // 登陆成功
                    Toast.makeText(MyApplication.getContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //第三步：验证参数是否正确，注册SDK
        if (params.validate()) {
            // 判断注册参数是否正确
            ECDevice.login(params);
        }
    }


}
