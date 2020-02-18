package com.zzq.beauty.model;

import java.util.Date;

public class BuyGoods {
    private Integer id;

    private Integer goodsid;

    private Float price;

    private String goodssnapshot;

    private Date createdate;

    private Integer num;

    private Integer state;

    private Date updatedate;

    private Integer personId;

    private Integer carerecordId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Integer goodsid) {
        this.goodsid = goodsid;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getGoodssnapshot() {
        return goodssnapshot;
    }

    public void setGoodssnapshot(String goodssnapshot) {
        this.goodssnapshot = goodssnapshot == null ? null : goodssnapshot.trim();
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

    public Integer getCarerecordId() {
        return carerecordId;
    }

    public void setCarerecordId(Integer carerecordId) {
        this.carerecordId = carerecordId;
    }
}