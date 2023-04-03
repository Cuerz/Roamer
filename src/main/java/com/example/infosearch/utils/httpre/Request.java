package com.example.infosearch.utils.httpre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Creater: Cuerz
 * Date: 2022/9/29
 */
public class Request {

    // HTTP GET请求
    public static String sendGet(String url) throws IOException {
        URL Url = new URL(url);
        HttpURLConnection con = (HttpURLConnection) Url.openConnection();

        // 默认值GET
        con.setRequestMethod("GET");

        StringBuffer response = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        // 返回结果
        return response.toString();

    }
}
