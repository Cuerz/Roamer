package com.example.infosearch.utils.hunter;

import com.example.infosearch.utils.httpre.Request;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * Creater: Cuerz
 * Date: 2022/9/29
 */
public class Hunter {

    public static String Search(String ApiUrl, String HunterKey, String Num, String Base64) throws IOException {
        String url = ApiUrl + "/openApi/search?api-key=" + HunterKey + "&search=" + Base64 + "&page=1&page_size=" + Num;
        return Request.sendGet(url);
    }
}
