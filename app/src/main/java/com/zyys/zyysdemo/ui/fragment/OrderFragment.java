package com.zyys.zyysdemo.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.zyys.zyysdemo.Api;
import com.zyys.zyysdemo.MyApplication;
import com.zyys.zyysdemo.R;
import com.zyys.zyysdemo.adapter.CheckOrderAdapter;
import com.zyys.zyysdemo.bean.OrderDetail;
import com.zyys.zyysdemo.custom.CustomDialogEditext;
import com.zyys.zyysdemo.ui.OrderActivity;
import com.zyys.zyysdemo.ui.OrderDetailsActivity;
import com.zyys.zyysdemo.utils.RefreshableView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 需要两个参数:appointment_id,voipAccount
 */

public class OrderFragment extends Fragment {

    private Button btnOrder, btnBack;
    private SwipeMenuListView listview_order;
    private RequestQueue mQueue;
    private ArrayList<OrderDetail> list = new ArrayList<>();
    private CheckOrderAdapter adapter;

    RefreshableView refreshableView;


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_fragment, container, false);
        btnOrder = (Button) view.findViewById(R.id.btn_fragment_order);
        btnBack = (Button) view.findViewById(R.id.btn_fragment_back);

        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);

        listview_order = (SwipeMenuListView) view.findViewById(R.id.listview_order);
        mQueue = Volley.newRequestQueue(getActivity());

        getOrderList();

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        listview_order.setMenuCreator(creator);
        listview_order.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open(需要传入订单id号去修改订单)
                        Toast.makeText(getActivity(), "open" + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);

//                        MySQLiteHelper helper = new MySQLiteHelper(MyApplication.getContext());
//                        ArrayList<Account> accounts = helper.selectAll();
//                        String voipaccount = accounts.get(position).getVoipaccount();

//                        Intent intent = getIntent();
//                        appointment_id = intent.getStringExtra("appointment_id");
//                        voipAccount = intent.getStringExtra("voipAccount");
//                        Log.d("OrderActivity","voipAccount=" + voipAccount);
//                        Log.d("OrderActivity", "appointment_id=" + appointment_id);

//                        intent.putExtra("account",voipaccount);
                        intent.putExtra("num", list.get(position).getNum());//number 就是id
                        startActivity(intent);
                        break;
                    case 1:
                        delete(list.get(position).getNum());
                        //1.删除数据库（服务器的订单id） 2.重新加载数据
//                        delete(position);//删除数据库一条订单
                        Toast.makeText(getActivity(), "delete", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //外面下单，需要填写容联云账号和密码
                showAlertDialog();//弹出对话框

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                getOrderList();//再次请求数据
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);

    }

    public void getOrderList() {//寄药订单列表(成功获取)
        //String url = "http://api.zhaoyang120.cn/api/drug/orders";
        String url = Api.ordersUrl;
        list = new ArrayList<>();
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                int num = object.getInt("id");
                                String name = object.getString("name");
                                String created_at = object.getString("created_at");
                                String doctor_name = object.getString("doctor_name");
                                int has_pay = object.getInt("has_pay");
                                OrderDetail orderDetail = new OrderDetail(num, name, created_at, doctor_name, has_pay);
                                list.add(orderDetail);
                            }
                            //适配数据
                            adapter = new CheckOrderAdapter(getContext(), list);
                            listview_order.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        mQueue.add(objectRequest);
    }

    //删除一条数据
    public void delete(final int num) {
        //String url = "http://api.zhaoyang120.cn/api/drug/delete-order";
        String url = Api.ordersUrl;

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("delete", "delete==" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CheckOrderActivity", "error:" + error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("id", num + "");
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        mQueue.add(request);
    }

    private void showAlertDialog() {//对话框
        final CustomDialogEditext dialogEditext = new CustomDialogEditext(getActivity(), R.style.Dialog);
        dialogEditext.setCanceledOnTouchOutside(true);
        View view = dialogEditext.getCustomView();
        final EditText order_appointment_id = (EditText) view.findViewById(R.id.order_appointment_id);
        final EditText order_voipAccount = (EditText) view.findViewById(R.id.order_voipAccount);
        Button positiveButton = (Button) view.findViewById(R.id.positiveButtonOrder);
        Button negativeButton = (Button) view.findViewById(R.id.negativeButtonOrder);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appointment_id = order_appointment_id.getText().toString();
                String voipAccount = order_voipAccount.getText().toString();
                if (!(appointment_id.equals("") && voipAccount.equals(""))) {
                    Intent intent = new Intent(MyApplication.getContext(), OrderActivity.class);
                    intent.putExtra("appointment_id", appointment_id);
                    intent.putExtra("voipAccount", voipAccount);
                    startActivity(intent);
                    dialogEditext.dismiss();
                } else {
                    Toast.makeText(MyApplication.getContext(), "appointment_id或voipAccount不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditext.dismiss();
            }
        });
        dialogEditext.show();
    }
}
