package com.zyys.zyysdemo.bean;

/**
 * Created by Administrator on 2016/3/8.
 */
public class Account {

    private String voipaccount;

    public Account(String voipaccount) {
        this.voipaccount = voipaccount;
    }

    public String getVoipaccount() {
        return voipaccount;
    }

    public void setVoipaccount(String voipaccount) {
        this.voipaccount = voipaccount;
    }

    @Override
    public String toString() {
        return voipaccount;
    }

}
