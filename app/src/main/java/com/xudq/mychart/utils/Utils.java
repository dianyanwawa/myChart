package com.xudq.mychart.utils;

import android.content.Context;

/**
 * Created by TJZM-14
 * AUTHOR: xudingqiang@teejo.com.cn
 * DATE: 2018/1/24
 * DESC:一般工具类
 */

public class Utils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     *
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String toString(Object o) {
        try {
            if (o != null) {
                return o.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int toInt(Object o){
        try {
            if(o !=null){
                return  Integer.valueOf(o.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
