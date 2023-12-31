package com.yc.dao;

import com.yc.bean.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;

//@Repository
public class AccountDaoJdbcTemplateImpl implements AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int insert(double money) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into accounts(balance) values(?)",new String[]{"accountid"});
            ps.setString(1,money+"");
            return ps;
        },keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(int accountid, double money) {
        this.jdbcTemplate.update("update  accounts set balance = ? where accountid=?",money+"",accountid+"");

    }

    @Override
    public void delete(int accountid) {
        this.jdbcTemplate.update("delete from accounts where accountid=?",Integer.valueOf(accountid));
    }

    @Override
    public int findCount() {
        int rowCount = this.jdbcTemplate.queryForObject("select count(*) from accounts",Integer.class);

        return rowCount;
    }

    @Override
    public List<Account> findAll() {
        List<Account> list = jdbcTemplate.query("select * from accounts",(resultSet, rowNum)->{
            Account a = new Account();
            a.setAccountid(resultSet.getInt(1));
            a.setMoney(resultSet.getDouble(2));
            return a;
        });
        return list;
    }

    @Override
    public Account findById(int accountid) {
        Account account  = jdbcTemplate.queryForObject("select * from accounts where accountid=?",(resultSet, rowNum)->{
            Account a = new Account();
            a.setAccountid(resultSet.getInt(1));
            a.setMoney(resultSet.getDouble(2));
            return a;
        },accountid);

        return account;
    }
}
