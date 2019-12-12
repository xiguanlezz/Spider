package com.cj.Spider.start;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cj.Spider.entity.ResultMap;
import com.cj.Spider.util.DynamicDownload;
import com.cj.Spider.util.SendEmailUtil;
import com.cj.Spider.util.SimulatedLoginUtil;
import com.cj.Spider.util.StringProcessUtil;

/*
 * 海贼王onepice执行入口
 */

public class StartCrawl {
    public static int newindex=0;
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
            StartCrawl.newindex=913;    //指定最新的集数
            if(Integer.parseInt(key)>StartCrawl.newindex) {
                SendEmailUtil.send();
            }
        }
    }

}
