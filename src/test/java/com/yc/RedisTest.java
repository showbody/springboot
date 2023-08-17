package com.yc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.bean.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
//    private RedisTemplate<String,Account> redisTemplate02;


    private Account account;

    //Json工具
    private static final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before() {
        account=new Account();
        account.setAccountid(1);
        account.setMoney(100);

    }
    @Test
    public void testSet(){
        this.redisTemplate.opsForValue().set("account", String.valueOf(account));
        System.out.println((redisTemplate.opsForValue().get("account")));

//        this.redisTemplate02.opsForValue().set("account", account);
//        System.out.println((redisTemplate02.opsForValue().get("account")));
    }

    @Test
    public void testStringTemplate() throws JsonProcessingException {
        //准备对象
        account=new Account();
        account.setAccountid(2);
        account.setMoney(100);
        //手动序列化
        String json = mapper.writeValueAsString(account);
        //写一条数据到redis
        stringRedisTemplate.opsForValue().set("account:02",json);
        //读取数据
        String val = stringRedisTemplate.opsForValue().get("account:02");
        //反序列化
        Account account1 = mapper.readValue(val,Account.class);
        System.out.println("account1="+account1);
    }
}

