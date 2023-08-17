package com.yc;

import com.yc.bean.Account;
import com.yc.biz.AccountBiz;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
@Slf4j
public class AccountBizImplTest extends TestCase {

    @Autowired
    private AccountBiz accountBiz;

    @Test
    public void findAccount() {
        Account a = accountBiz.findAccount(3);
        Assert.assertNotNull(a);
        log.info(a.toString());
    }

    @Test
    public void openAccount() {
        Account a = accountBiz.openAccount(100);
        Assert.assertNotNull(a);
        log.info(a.toString());
    }

    @Test
    public void deposite() {
        Account a = accountBiz.deposite(1,1);
        Assert.assertNotNull(a);
        log.info(a.toString());
    }


    @Test
    public void withdraw() {
        Account a = accountBiz.withdraw(3,9);
        Assert.assertNotNull(a);
        log.info(a.toString());
    }


    @Test
    public void transfer() {
        Account a = accountBiz.transfer(1,5,4);
        Assert.assertNotNull(a);
        log.info(a.toString());
    }
}