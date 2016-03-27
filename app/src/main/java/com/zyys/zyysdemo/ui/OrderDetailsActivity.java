package com.zyys.zyysdemo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zyys.zyysdemo.Api;
import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.bean.RootInfo;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {

    private RequestQueue mQueue ;
    private EditText etPeople,  etPhone,  etAddress,  etMedicine, etPrice,  etBrokerage, etRemark;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revise_order);
        mQueue = Volley.newRequestQueue(OrderDetailsActivity.this);
        etPeople = (EditText) findViewById(R.id.etPeople);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etMedicine = (EditText) findViewById(R.id.etMedicine);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etBrokerage = (EditText) findViewById(R.id.etBrokerage);
        etRemark = (EditText) findViewById(R.id.etRemark);

        Intent intent = getIntent();
        num = intent.getIntExtra("num", 110);
        //voipaccount = intent.getStringExtra("voipaccount");
        submitGet(num);
    }

    public void submitGet(int num) {//寄药订单详情（获取id为空）（已经解决：拼接接口）
//        String url = "http://api.zhaoyang120.cn/api/drug/order?id=" + num;
        String url = Api.OrderDetailsUrl + num;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        RootInfo rootInfo = new Gson().fromJson(response, RootInfo.class);

                        etPeople.setText(rootInfo.getData().getName());
                        etPhone.setText(rootInfo.getData().getPhone());
                        etAddress.setText(rootInfo.getData().getAddress());
                        etMedicine.setText(rootInfo.getData().getDrug());
                        etPrice.setText(rootInfo.getData().getMoney());
                        etBrokerage.setText(rootInfo.getData().getCommission());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        mQueue.add(request);
    }

    //点击修改保存

    //点击保存，修改一条数据
    public void submitAddNewOrder() {//修改寄药订单（）
//        String url = "http://api.zhaoyang120.cn/api/drug/order";
        String url = Api.orderUrl;
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(OrderDetailsActivity.this, "修改订单成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderDetailsActivity.this, "修改订单失败!", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();

                if (!(etPeople.getText().toString().equals("") && etPhone.getText().toString().equals("")
                        && etAddress.getText().toString().equals("") && etMedicine.getText().toString().equals("")
                        && etPrice.getText().toString().equals(""))) {
                    String etPeopleText = etPeople.getText().toString();
                    String etPhoneText = etPhone.getText().toString();
                    String etAddressText = etAddress.getText().toString();
                    String etMedicineText = etMedicine.getText().toString();
                    String etPriceText = etPrice.getText().toString();
                    //有id，修改数据
                    param.put("id", num+"");//为空时候新增加数据
                    param.put("to", etPeopleText);//收件人
                    param.put("money", etPriceText);//订单金额,价格
                    param.put("drug", etMedicineText);//药物信息
                    param.put("address", etAddressText);//地址
                    param.put("phone", etPhoneText);//电话
                    //从查看订单页进入，查看订单详细信息，（未付款）可修改
                    //param.put("appointment_id", appointment_id);//预约单id
                    //param.put("voipAccount", voipaccount);//唯一标识
                } else {
                    Toast.makeText(OrderDetailsActivity.this, "请填写完整数据再保存!", Toast.LENGTH_SHORT).show();
                }
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> header = new HashMap<String, String>();
                return header;
            }
        };
        mQueue.add(request);
    }

    public void btnReviseOrder(View view) {
        //修改订单
        submitAddNewOrder();
    }
}
