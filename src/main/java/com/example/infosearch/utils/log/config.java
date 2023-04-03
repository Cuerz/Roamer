package com.example.infosearch.utils.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creater: Cuerz
 * Date: 2022/9/29
 */
public class config {

    public static String GetTime() {
        // 格式化时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        // a为am/pm的标记
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss a");
        // 获取当前时间
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
