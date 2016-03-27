package com.zyys.zyysdemo.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.zyys.zyysdemo.R;

/**
 * Created by Administrator on 2016/3/14.
 */
public class CustomDialogTest extends Dialog {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(customView);
    }

    private  View customView;

    public CustomDialogTest(Context context) {
        super(context);

    }

    public CustomDialogTest(Context context, int themeResId) {
        super(context, themeResId);

        LayoutInflater inflater= LayoutInflater.from(context);
        customView = inflater.inflate(R.layout.dialog_normal_layout, null);
    }

    protected CustomDialogTest(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    public View getCustomView() {
        return customView;
    }

}
