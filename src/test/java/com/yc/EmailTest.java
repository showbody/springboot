package com.yc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
@Slf4j
public class EmailTest {
    @Autowired
    JavaMailSenderImpl javaMailSender;


//    @Scheduled(cron = "0/5 * * * * ?  ")
    @Test
    public void mailTest(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //标题
        mailMessage.setSubject("hello，正在发邮箱");
        //内容
        mailMessage.setText("测试成功！");
        //收信人
        mailMessage.setTo("1809785064@qq.com");
        //发信人
        mailMessage.setFrom("1158971203@qq.com");
        //开始发送
        javaMailSender.send(mailMessage);

    }

}
