package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Account;
import com.yc.bean.OpRecord;
import com.yc.bean.OpType;
import com.yc.mappers.AccountMapper;
import com.yc.mappers.OpRecordMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: mavenProject
 * @description:
 * @author: Joker
 * @create: 2023-08-02 14:15
 */
@Service
@Log4j2
@Transactional
public class AccountBizImpl implements AccountBiz {

//    @Autowired
//    private AccountDao accountDao;
//
//    @Autowired
//    private OpRecordDao opRecordDao;

    @Autowired
    private AccountMapper accountDao;

    @Autowired
    private OpRecordMapper opRecordDao;

    @Autowired
//    private RedisTemplate<String,OpRecord> redisTemplate;
    private RedisTemplate redisTemplate;


    @Override
    public void addAccount(int accountid, double money) {
        System.out.println("添加账户:" + accountid);
    }

    @Override
    public List<OpRecord> findOpRecord(String date) {
        List<OpRecord> list = new ArrayList<>();
        //验证date格式是否正确
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d= null;
        try{
            d = df.parse(date);
        }catch (ParseException pe){
            pe.printStackTrace();
            log.error("待查询的日志格式不正确，原格式为:yyyy-MM-dd");
            throw new RuntimeException();

        }
        //先查询缓存，缓存有则取缓存中的
        //日期：List<OpRecord> 存到redis中的OpRecord是json字符串，=> 序列化 =>OpRecord类实现java.io.Serializable
        if (redisTemplate.hasKey(date)){
            //redisTemplate.opsForList().size(date)查看date键下有多少条数据
            //redisTemplate.opsForList().range(键，0，取出的数据的条数)
            list = redisTemplate.opsForList().range(date,0, redisTemplate.opsForList().size(date));
            log.info("从缓存的键："+date+",取出的值list为:"+list);
            return list;
        }
        //再查数据库，select * from opRecord where optime between startdate and enddate
        QueryWrapper wrapper = new QueryWrapper();
        //计算最后一天
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE,1);
        Date nextDate= cal.getTime();
        String nextDateString = df.format(nextDate);
        //where optime between startdate and enddate
        wrapper.between("optime",date,nextDateString);
        list = opRecordDao.selectList(wrapper);
        if (list!=null&&list.size()>0){
            redisTemplate.delete(date);//先清空原来的
            redisTemplate.opsForList().leftPush(date, list);
            redisTemplate.expire(date,15, TimeUnit.DAYS);//15天的过期时间
        }
        return list;
    }

    @Override
    public Account openAccount(double money) {
        //开户操作 返回新账号的 id
//        int accountid = this.accountDao.insert(money);
        Account newAccount = new Account();
        newAccount.setMoney(money);
        this.accountDao.insert(newAccount);

        //包装日志信息
        OpRecord opRecord = new OpRecord();
        opRecord.setAccountid(newAccount.getAccountid());
        opRecord.setOpmoney(money);
        opRecord.setOptype(OpType.DEPOSITE);

        this.opRecordDao.insert(opRecord);
        //返回新账号的信息
//        Account a = new Account();
//        a.setAccountid(accountid);
//        a.setMoney(money);
        return newAccount;
    }

    @Override
    public Account deposite(int accountid, double money) {
        return this.deposite(accountid,money,null);
    }

    @Override
    public Account deposite(int accountid, double money, Integer transferId) {
        Account a = null;
        try {
//            a = this.accountDao.findById(accountid);
            a = this.accountDao.selectById(accountid);
        }catch (RuntimeException re){

            log.error(re.getMessage());
            throw new RuntimeException("查无此账户" + accountid + "无法完成存款操作");
        }
        if (a==null){
            throw new RuntimeException("查无此账户" + accountid + "无法完成存款操作");
        }
        //存款时 金额累加
        a.setMoney(a.getMoney() + money);

//        this.accountDao.update(accountid,a.getMoney());
        this.accountDao.updateById(a);//方案一

        OpRecord opRecord = new OpRecord();
        opRecord.setAccountid(accountid);
        opRecord.setOpmoney(money);
        if(transferId != null){
            opRecord.setOptype(OpType.TRANSFER);
            opRecord.setTransferid(transferId);
        }else {
            opRecord.setOptype(OpType.DEPOSITE);
        }
        this.opRecordDao.insert(opRecord);
        return a;
    }

    @Override
    public Account withdraw(int accountid, double money) {
        return this.withdraw(accountid,money,null);
    }

    @Override
    public Account withdraw(int accountid, double money, Integer transferId) {
        Account a = null;
        try {
//            a = this.accountDao.findById(accountid);
            a = this.accountDao.selectById(accountid);
        }catch (RuntimeException re){

            log.error(re.getMessage());
            throw new RuntimeException("查无此账户" + accountid + "无法完成取款操作");
        }
        //取款时 金额相减
        a.setMoney(a.getMoney() - money);

        OpRecord opRecord = new OpRecord();
        opRecord.setAccountid(accountid);
        opRecord.setOpmoney(money);
        if(transferId != null){
            opRecord.setOptype(OpType.TRANSFER);
            opRecord.setTransferid(transferId);
        }else {
            opRecord.setOptype(OpType.WITHDRAW);
        }
//        this.opRecordDao.insertOpRecord(opRecord); //先插入日志
        this.opRecordDao.insert(opRecord);
        this.accountDao.updateById(a); //再减金额
        return a;
    }

    @Override
    public Account transfer(int accounId, double money, int toAccountId) {
        this.deposite(toAccountId,money,accounId); //收款方
        Account a = this.withdraw(accounId,money,toAccountId);
        return a;
    }

    @Override
    public Account findAccount(int accountid) {
        return this.accountDao.selectById(accountid);
    }

    @Override
    public Double findTotalBalance() {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.select("sum(balance) as total");
        List<Map<String,Object>> list = accountDao.selectMaps(wrapper);
        if (list!=null&&list.size()>0){
            Map<String,Object> map = list.get(0);
            if (map.containsKey("total")){
                return Double.parseDouble(map.get("total").toString());
            }
        }
        return 0.0;
    }
}
