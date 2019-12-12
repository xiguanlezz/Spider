package com.cj.Spider.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class StringProcessUtil {

    public static Map<String, String> cutForonepice(Map<String, String> m) {
        Map<String, String> rs = new LinkedHashMap<String, String>();
        String right = "340";
        int index1, index2, index3;
        String newkey, newvalue;
        for (String key : m.keySet()) {
            index1 = key.indexOf(":", 0);
            newkey = key.substring(index1 + 1, key.length());
            String value = m.get(key);
            index2 = value.indexOf(":", 0);
            index3 = value.indexOf(",", 0);
            newvalue = value.substring(index2 + 2, index3 - 1);
            rs.put(newkey, newvalue);
        }
        return rs;
    }

    public static String produceFornewurl(String url, String keyword) {
        String newURL = null;
        String encodekeyword = null;
        int index_point = url.indexOf(".");
        int index_www = url.indexOf("w");
        newURL = url.substring(0, index_www) + "so"
                + url.substring(index_point);

        try {
            // URL中的中文要转成UTF-8
            encodekeyword = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        newURL = newURL + "so/q_" + encodekeyword;
        return newURL;
    }

    public static void main(String[] args) {

    }

}
