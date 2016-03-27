package com.zyys.zyysdemo.utils;

import com.yuntongxun.ecsdk.ECInitParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/3.
 */
public class HttpUtils {//single modle

    private static ECInitParams params;// = ECInitParams.createParams();

    private HttpUtils() {
        //私有化，防止被创建新的对象
    }

    public static ECInitParams getParams(){
        if (params == null){
            params = ECInitParams.createParams();
        }
        return params;
    }

}
