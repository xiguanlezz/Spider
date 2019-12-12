package com.cj.Spider.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.cj.Spider.entity.ResultMap;

public class DynamicDownload {

    public static Map<String, String> dynamicdownloadForonePage(String url,
                                                                int pageindex) {

        String content = null;
        String oldurl = "https://pcw-api.iqiyi.com/albums/album/avlistinfo?aid=202861101&size=50&page=";
        String newurl = oldurl + pageindex;
        StringBuilder json = new StringBuilder();
        URLConnection uc = null;
        BufferedReader in = null;
        String str = null;
        URL urlobject = null;

        // 根据URL直接抓取json包
        try {
            urlobject = new URL(newurl);
            uc = urlobject.openConnection();
            in = new BufferedReader(new InputStreamReader(
                    urlobject.openStream(), "UTF-8"));
            while ((str = in.readLine()) != null)
                json.append(str);
            if (in != null)
                in.close();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 清洗需要的内容,并放到map里面
        content = json.toString();
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode rn = cleaner.clean(content);
        Map<String, String> rs = new LinkedHashMap<String, String>();
        String temp1, temp2;
        Pattern p_number = Pattern.compile("\"order\":\\d+");
        Pattern p_name = Pattern.compile("subtitle\":\"[^\"]+\",\"vid");
        Matcher m_number = p_number.matcher(rn.getText().toString());
        Matcher m_name = p_name.matcher(rn.getText().toString());
        while (m_number.find() && m_name.find()) {
            temp1 = m_number.group(0);
            temp2 = m_name.group(0);
            rs.put(temp1, temp2);
        }

        // 修复爱奇艺页面中的340集名字残缺
        List<String> list = new ArrayList<>();
        int flag = 0, i = 0;
        if (pageindex == 7) {
            for (String key : rs.keySet()) {
                if ("\"order\":340".equals(key))
                    flag = 1;
                if (flag == 1) {
                    String value = rs.get(key);
                    list.add(value);
                }
            }
            flag = 0;
            for (String key : rs.keySet()) {
                if ("\"order\":340".equals(key)) {
                    flag = 1;
                    rs.put(key, "subtitle\":\"被称为天才的男人！ 霍古巴可现身\",\"vid");
                    continue;
                }
                if (flag == 1) {
                    rs.put(key, list.get(i++));
                }
            }
            // rs.put("\"order\":350", list.get(i++));
        }
        return rs;

    }

    public static void main(String[] args) {
        ResultMap object = new ResultMap();
        Map<String, String> des_map = new LinkedHashMap<String, String>();
        String url = "https://www.iqiyi.com";
        String des_url = SimulatedLoginUtil.getnewURL(url);
        for (int i = 1; i <= 19; i++) {
            Map<String, String> rs = DynamicDownload.dynamicdownloadForonePage(
                    des_url, i);
            des_map.putAll(rs);
            System.out.println("第" + i + "页  已经抓取完成");
        }
        object.setTree(des_map);
        object.setTree(StringProcessUtil.cutForonepice(object.getTree()));
        System.out.println("全部抓取完毕");
        for (String key : object.getTree().keySet()) {
            String value = object.getTree().get(key);
            System.out.println(key + "  " + value);
        }
    }
}
