package com.zyys.zyysdemo.bean;

/**
 * Created by Administrator on 2016/3/6.
 */
public class RootInfo {

    private int status;
    private OrderDetail data;

    public RootInfo(int status, OrderDetail data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderDetail getData() {
        return data;
    }

    public void setData(OrderDetail data) {
        this.data = data;
    }
}
