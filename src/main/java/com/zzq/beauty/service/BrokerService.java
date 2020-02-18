package com.zzq.beauty.service;

import com.zzq.beauty.model.Broker;
import com.zzq.beauty.util.PageBean;

import java.util.List;
import java.util.Map;

public interface BrokerService {
    void insert (Broker broker);

    /**
     * 更新
     * @param  broker
     */
    void update(Broker broker);


    void updateBrokerSelective(Broker broker);

    /**
     * 获取所有客户的推荐人
     * @param keyWord
     * @return
     */
    PageBean<List<Map<String,Object>>> getAllPuller(String user,String puller,Integer pageNum,Integer pageSize);


}
