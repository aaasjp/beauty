package com.zzq.beauty.model;

/**
 * 统计
 */
public class Statistic {
    private Float cashSumOfKaidan;
    private Float vipCardSumOfKaidan;
    private Float cashSumOfBanka;
    private Float cashSumOfXuka;
    private Float totalCash;
    private Float totalVipCard;
    private Integer sumOfnewCustomer;
    private Integer sumOfoldCustomer;

    public Float getCashSumOfKaidan() {
        return cashSumOfKaidan;
    }

    public void setCashSumOfKaidan(Float cashSumOfKaidan) {
        this.cashSumOfKaidan = cashSumOfKaidan;
    }

    public Float getVipCardSumOfKaidan() {
        return vipCardSumOfKaidan;
    }

    public void setVipCardSumOfKaidan(Float vipCardSumOfKaidan) {
        this.vipCardSumOfKaidan = vipCardSumOfKaidan;
    }

    public Float getCashSumOfBanka() {
        return cashSumOfBanka;
    }

    public void setCashSumOfBanka(Float cashSumOfBanka) {
        this.cashSumOfBanka = cashSumOfBanka;
    }

    public Float getCashSumOfXuka() {
        return cashSumOfXuka;
    }

    public void setCashSumOfXuka(Float cashSumOfXuka) {
        this.cashSumOfXuka = cashSumOfXuka;
    }

    public Float getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(Float totalCash) {
        this.totalCash = totalCash;
    }

    public Float getTotalVipCard() {
        return totalVipCard;
    }

    public void setTotalVipCard(Float totalVipCard) {
        this.totalVipCard = totalVipCard;
    }

    public Integer getSumOfnewCustomer() {
        return sumOfnewCustomer;
    }

    public void setSumOfnewCustomer(Integer sumOfnewCustomer) {
        this.sumOfnewCustomer = sumOfnewCustomer;
    }

    public Integer getSumOfoldCustomer() {
        return sumOfoldCustomer;
    }

    public void setSumOfoldCustomer(Integer sumOfoldCustomer) {
        this.sumOfoldCustomer = sumOfoldCustomer;
    }
}