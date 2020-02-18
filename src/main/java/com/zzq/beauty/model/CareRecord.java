package com.zzq.beauty.model;

import java.util.Date;

public class CareRecord {
    private Integer id;

    private Integer personid;

    private Integer userid;

    private Date createdate;

    private Date updatedate;

    private Date servicedate;

    private Float amount;

    private String memo;

    private String payType;

    private Integer buyVipCardId;

    private Integer operatorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonid() {
        return personid;
    }

    public void setPersonid(Integer personid) {
        this.personid = personid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
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

    public Integer getBuyVipCardId() {
        return buyVipCardId;
    }

    public void setBuyVipCardId(Integer buyVipCardId) {
        this.buyVipCardId = buyVipCardId;
    }

    public Date getServicedate() {
        return servicedate;
    }

    public void setServicedate(Date servicedate) {
        this.servicedate = servicedate;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}