package com.cj.Spider.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendEmailUtil {

    public static void send() {
        final String send_account = "cj1561435010@163.com";
        final String send_password = ReadFileUtil.readTextFile("D:\\IDEAproject\\Spider\\pwd.txt").trim();    // 密码或者是你自己的设置的授权码

        // SMTP服务器
        String MEAIL_163_SMTP_HOST = "smtp.163.com";
        String SMTP_163_PORT = "25";// 端口号,这个是163使用到的;
        // 收件人
        String receive_account = "cj1561435010@163.com";
        Properties p = new Properties(); //创建连接对象
        //Properties.
        p.put("mail.transport.protocol", "smtp");  //设置邮件发送的协议
        p.setProperty("mail.smtp.host", "smtp.163.com");   //设置发送邮件的服务器(这里用的163-SMTP服务器)
        p.setProperty("mail.smtp.auth", "true");   //设置经过账号密码的授权
        //设置服务器的端口号
        p.setProperty("mail.smtp.port", "25");
        p.setProperty("mail.smtp.socketFactory.port", "25");
        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(p, new Authenticator() {
            // 设置认证账户信息
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(send_account, send_password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);   //创建邮件对象
            message.setFrom(new InternetAddress(send_account)); //设置发件人
            message.setRecipients(Message.RecipientType.TO, receive_account);
            message.setRecipients(Message.RecipientType.CC, send_account); //重点：最好写这个抄送，不然大概率会被当成垃圾邮件！！
            message.setFrom(new InternetAddress(send_account));
            message.setSubject("one pice!");
            message.setText("海贼王更新了！！！");
            message.setSentDate(new Date());
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
