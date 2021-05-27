package wq.study.demo.entity;

import lombok.Data;

@Data
public class UserInfo {
    private int id;
    private String loginName;
    private String loginPassword;
    private String userName;
    private int sex;
    private int age;
    private String phone;
    private String work;
    private String address;
    private long birthday;
    private int payMoney;
    private int balance;
    private long createTime;
}