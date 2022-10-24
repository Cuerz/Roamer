package com.example.infosearch.utils.fofa;

import com.example.infosearch.utils.httpre.Request;

import java.io.IOException;

/**
 * Creater: Cuerz
 * Date: 2022/10/6
 */
public class Fofa {
    public static String Search(String ApiUrl, String email, String ApiKey, String Num, String Base64) throws IOException {
        String url = ApiUrl + "/api/v1/search/all?email=" + email + "&key=" + ApiKey + "&size=" + Num + "&qbase64=" + Base64 + "&fields=ip,port,domain,title,server,city&full=true";
        return Request.sendGet(url);
    }
}
