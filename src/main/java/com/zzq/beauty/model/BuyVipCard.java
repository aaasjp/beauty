package com.zzq.beauty.model;

import java.util.Date;

public class BuyVipCard {
    private Integer id;

    private Integer vipCardId;

    private Float price;

    private Float available;

    private Float remainder;

    private String vipCardSnapshot;

    private Date createdate;

    private Integer num;

    private Integer state;

    private Date updatedate;

    private Integer personId;

    private String memo;

    private String payType;

    private String type;

    private Integer userId;

    private Integer operatorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipCardId() {
        return vipCardId;
    }

    public void setVipCardId(Integer vipCardId) {
        this.vipCardId = vipCardId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getAvailable() {
        return available;
    }

    public void setAvailable(Float available) {
        this.available = available;
    }

    public Float getRemainder() {
        return remainder;
    }

    public void setRemainder(Float remainder) {
        this.remainder = remainder;
    }

    public String getVipCardSnapshot() {
        return vipCardSnapshot;
    }

    public void setVipCardSnapshot(String vipCardSnapshot) {
        this.vipCardSnapshot = vipCardSnapshot;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}