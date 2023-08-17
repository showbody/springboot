package com.yc.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;

//@Configuration
public class EmailConfig {

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void snedmail(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //标题
        mailMessage.setSubject("正在发邮箱");
        //内容
        mailMessage.setText("ok！");
        //收信人
        mailMessage.setTo("1809785064@qq.com");
        //发信人
        mailMessage.setFrom("1158971203@qq.com");
        //开始发送
        javaMailSender.send(mailMessage);

    }
}
