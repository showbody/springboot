package com.yc.controller;


import com.yc.bean.Account;
import com.yc.bean.OpRecord;
import com.yc.biz.AccountBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "Account控制器API")

public class BankController {
    @Autowired
    private AccountBiz accountBiz;

    @ApiOperation(value = "开户操作", notes = "输入开户金额进行开户")
    @PostMapping("/openAccount")
    public Account openAccount(double money){
        return accountBiz.openAccount(money);
    }

    @ApiOperation(value = "存款操作", notes = "根据用户id和存款金额进行存款操作")
    @PostMapping("/deposite")
    public Account deposite(int accountid,double money){
        return accountBiz.deposite(accountid,money);
    }

    @ApiOperation(value = "取款操作", notes = "根据用户id和取款金额进行取款操作")
    @PostMapping("/withdraw")
    public Account withdraw(int accountid,double money){
        return accountBiz.withdraw(accountid,money);
    }

    @ApiOperation(value = "转账", notes = "根据转账的双方的id和转账的金额进行转账操作")
    @PostMapping("/transfer")
    public Account transfer(int accountId,double money,int toAccountId){
        return accountBiz.transfer(accountId,money,toAccountId);
    }

    @ApiOperation(value = "获取Account", notes = "根据id查询账户信息获取Account")
    @PostMapping("/findAccount")
    public Account findAccount(int accountId){
        return accountBiz.findAccount(accountId);
    }

    @ApiOperation(value = "查询指定日期的日志", notes = "输入日期")
    @PostMapping("/findOpRecord")
    public Map findOpRecord(String date){
        Map result = new HashMap();
        try{
            List<OpRecord> list = this.accountBiz.findOpRecord(date);
            result.put("code",1);
            result.put("data",list);
        }catch (RuntimeException re){
            result.put("code",0);
            result.put("errMsg",re.getMessage());
            re.printStackTrace();
        }

        return result;
    }

}
