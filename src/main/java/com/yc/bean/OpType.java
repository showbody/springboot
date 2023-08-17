package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum OpType implements IEnum<String> {
    WITHDRAW("withdraw",1),DEPOSITE("deposite",2),TRANSFER("transfer",3);

    private String key;
    private int value;
    OpType(String key, int value) {
        this.key = key;
        this.value = value;
    }
    //取枚举中的哪个数据存到数据库

    @Override
    public String getValue() {
        return this.key;
    }

    public String getKey() {
        return key;
    }

}
