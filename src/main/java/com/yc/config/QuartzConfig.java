package com.yc.config;


import com.yc.biz.AccountBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Quartz定时任务配置类
 * @author pan_junbiao
 **/
//@Configuration
public class QuartzConfig{
    @Autowired
    private AccountBiz accountBiz;

    @Scheduled(cron = "0/30 * * * * ? ")
    public void getToTal(){
        Double total = accountBiz.findTotalBalance();
        System.out.println("银行总余额为:"+total);
    }

    @Scheduled(cron = "0/5 * * * * ? ")
    public void show(){
        System.out.println("hello world");
    }


}