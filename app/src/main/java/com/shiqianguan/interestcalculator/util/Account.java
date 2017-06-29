package com.shiqianguan.interestcalculator.util;

import java.math.BigDecimal;

/**
 * Created by guans on 2017/6/28.
 */
/*
 *计算利息和本金的工具类
 * 提供一个实例方法和静态方法
 */

public class Account {
    private String userName;
    private BigDecimal annualInterest;
    private BigDecimal balance;

    //默认的构造函数，初始化账户金额和利率；
    public Account() {
        userName = "Default";
        setBalance(0.00);
        setBalance(0.00);
    }

    /*
     *实现利率计算的静态方法；
     @param inputBalance 输入的账户初始金额
     @param  inputAnnualInterest 要输入的年利率
     @param years 输入的存入年限
     @return 计算出来的本金+利息总金额，是一个BigDcimal值，结果可以使用BigDecimal的方法转化为常规数值
     */
    public static BigDecimal getTotalBalance(double inputBalance, double inputAnnualInterest, int years) {
        //对输入的年限进行检查，如果为负数则直接抛出异常，再进行处理
        if (years < 0)
            throw new IllegalArgumentException("Years cannot be negative");
        BigDecimal balance = new BigDecimal(inputBalance);
        BigDecimal annualInterest = new BigDecimal(inputAnnualInterest);
        BigDecimal growthRate = annualInterest.add(new BigDecimal(1));
        for (int i = 0; i <= years - 1; i++) {
            balance = balance.multiply(growthRate);
        }
        return balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getAnnualInterest() {
        return annualInterest;
    }

    public void setAnnualInterest(double annualInterest) {
        this.annualInterest = new BigDecimal(annualInterest);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = new BigDecimal(balance);
    }

    /*
     * 结果计算的一个实例方法
     * 利息计算，使用的是复利的计算方式；
     * 由于题意要求是整年存取，故只考虑存取年限为整年的判断，不再对没存够整年的情况进行分析（否则的话要对年份和利息规则进行计算）
     * 考虑到精度使用BigDecimal进行计算;
     */
    public BigDecimal getTotalBalanceByInstance(double inputBalance, double inputAnnualInterest, int years) {
        //首先进行输入年限的非负判断
        if (years < 0)
            throw new IllegalArgumentException("Years cannot be negative");
        BigDecimal growthRate = annualInterest.add(new BigDecimal(1));
        setAnnualInterest(inputAnnualInterest);
        setBalance(inputBalance);
        for (int i = 0; i <= years - 1; i++) {
            balance = balance.multiply(growthRate);
        }
        return balance;
    }

}
