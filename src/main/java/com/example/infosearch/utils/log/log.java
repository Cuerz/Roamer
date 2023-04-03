package com.example.infosearch.utils.log;

/**
 * Creater: Cuerz
 * Date: 2022/9/29
 */
public class log {

    public static String INFO(String data) {
        return config.GetTime() + " [INFO] [*] " + data;
    }

    public static String DEBUG(String data) {
        return config.GetTime() + " [DEBUG] [*] " + data;
    }

    public static String ERROR(String data) {
        return config.GetTime() + " [ERROR] [-] " + data;
    }

    public static String SUCCESS(String data) {
        return config.GetTime() + " [SUCCESS] [+] " + data;
    }
}
