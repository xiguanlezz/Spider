package com.cj.Spider.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SimulatedLoginUtil {

    public static String getnewURL(String url) {

        // jsoup模拟海贼王超链接的点击事件,返回新的URL
        String newURL = null;
        System.getProperties().setProperty("proxySet", "true");
        // System.getProperties().setProperty("https.proxyHost",
        // "192.168.130.15");
        // 代理端口
        // System.getProperties().setProperty("https.proxyPort", "8848");
        Connection con = Jsoup.connect(url);// 获取连接

        con.header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");// 配置模拟浏览器
        con.header("Accept-Language", "zh-CN");
        Response rs = null;
        try {
            rs = con.execute();
            Document dom = Jsoup.parse(rs.body());// 转换为Dom树
            List<Element> et = dom.select(".search-box-input");
            Element inputtext = et.get(0).attr("value", "海贼王");
            String des_keyword = inputtext.attr("value");
            newURL = StringProcessUtil.produceFornewurl(url, des_keyword);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // htmlunit模拟网页点击,选中要查看的海贼王非剧场版动漫
        WebClient client = new WebClient(BrowserVersion.getDefault());
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setTimeout(10000);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setRedirectEnabled(true);
        // client.getCookieManager().setCookiesEnabled(true);
        // client.getOptions().setUseInsecureSSL(true);
        client.setAjaxController(new NicelyResynchronizingAjaxController());

        String finallyURL = null;
        HtmlPage page = null, next_page = null;
        int k = 0, flag = 0;
        try {
            page = client.getPage(newURL);
            System.out.println(newURL);
            DomNodeList<DomElement> domlist = page.getElementsByTagName("a");
            for (int i = 0; i < domlist.getLength(); i++) {
                DomElement domElement = domlist.get(i);
                if (domElement.asText().equals("航海王")) {
                    k = i;
                    flag = 1;
                    break;
                }
            }
            if (flag == 0)
                System.out.println("没找到超链接a");
            HtmlElement e = (HtmlElement) domlist.get(k);
            client.waitForBackgroundJavaScript(3000);
            next_page = e.click();
            client.waitForBackgroundJavaScript(3000);
            finallyURL = next_page.getUrl().toString();

        } catch (FailingHttpStatusCodeException e3) {
            e3.printStackTrace();
        } catch (MalformedURLException e3) {
            e3.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return finallyURL;
    }

    public static void main(String[] args) {
        String url = "https://www.iqiyi.com/";
        String finallyurl = "";
        finallyurl = SimulatedLoginUtil.getnewURL(url);
        System.out.println(finallyurl);
    }

}
