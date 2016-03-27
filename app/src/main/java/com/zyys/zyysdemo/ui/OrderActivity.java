package com.zyys.zyysdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zyys.zyysdemo.Api;
import com.zyys.zyysdemo.R;

import java.util.HashMap;
import java.util.Map;


public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_save_order;
    private EditText etPeople, etPhone, etAddress, etMedicine, etPrice;
    private RequestQueue mQueue;

    private String appointment_id;
    private String voipAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mQueue = Volley.newRequestQueue(OrderActivity.this);

        etPeople = (EditText) findViewById(R.id.etPeople);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etMedicine = (EditText) findViewById(R.id.etMedicine);
        etPrice = (EditText) findViewById(R.id.etPrice);

        btn_save_order = (Button) findViewById(R.id.btn_save_order);
        btn_save_order.setOnClickListener(this);

        Intent intent = getIntent();
        appointment_id = intent.getStringExtra("appointment_id");
        voipAccount = intent.getStringExtra("voipAccount");
    }

    //点击保存，修改一条数据
    public void submitAddNewOrder() {//新增一条数据
//        String url = "http://api.zhaoyang120.cn/api/drug/order";
        String url = Api.orderUrl;
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(OrderActivity.this, "新增一条订单成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this, "新增一条订单失败，请输入正确的：appointment_id", Toast.LENGTH_SHORT).show();
                        finish();
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

                    //新增加数据
                    param.put("id", "");//为空时候新增加数据
                    param.put("to", etPeopleText);//收件人
                    param.put("money", etPriceText);//订单金额,价格
                    param.put("drug", etMedicineText);//药物信息
                    param.put("address", etAddressText);//地址
                    param.put("phone", etPhoneText);//电话
                    param.put("appointment_id", appointment_id);//预约单id
                    param.put("voipAccount", voipAccount);//唯一标识
                } else {
                    Toast.makeText(OrderActivity.this, "请填写完整数据再保存!", Toast.LENGTH_SHORT).show();
                }
                return param;
            }
        };
        mQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        //点击保存，新增一条数据
        submitAddNewOrder();
    }

    public void btnBack(View view) {
        finish();
    }
}
