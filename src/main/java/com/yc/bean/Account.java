package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("accounts")
@ApiModel("账户实体类")
public class Account implements Serializable {

    @ApiModelProperty("账户主键，默认自增")
    @TableId(type = IdType.AUTO)
    private int accountid;

    @ApiModelProperty("账户余额")
    @TableField("balance")
    private double money;
}
