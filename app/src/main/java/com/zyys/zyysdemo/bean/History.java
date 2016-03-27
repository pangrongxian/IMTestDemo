package com.zyys.zyysdemo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/9.
 */
public class History {

    private ArrayList<Msg> historyList;

    public History(ArrayList<Msg> historyList) {
        this.historyList = historyList;
    }

    public ArrayList<Msg> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(ArrayList<Msg> historyList) {
        this.historyList = historyList;
    }

    @Override
    public String toString() {
        return "History{" +
                "historyList=" + historyList +
                '}';
    }
}
