package com.zzq.beauty.model;

import java.util.Date;

/**
 * 推荐人
 */
public class Broker {
    private Integer id;

    private Integer client;

    private Integer puller;

    private Date startdate;

    private Date enddate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getPuller() {
        return puller;
    }

    public void setPuller(Integer puller) {
        this.puller = puller;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}