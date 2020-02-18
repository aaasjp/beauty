package com.zzq.beauty.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzq.beauty.mapper.BrokerMapper;
import com.zzq.beauty.model.Broker;
import com.zzq.beauty.service.BrokerService;
import com.zzq.beauty.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BrokerServiceImpl implements BrokerService{
    @Autowired
    private BrokerMapper brokerMapper;
    @Override
    public void insert(Broker broker) {
        brokerMapper.insert(broker);
    }

    @Override
    public void update(Broker broker) {
        brokerMapper.updateByPrimaryKey(broker);
    }

    @Override
    public void updateBrokerSelective(Broker broker) {
        brokerMapper.updateByPrimaryKeySelective(broker);
    }

    @Override
    public PageBean<List<Map<String, Object>>> getAllPuller(String user,String puller,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<List<Map<String,Object>>> page= brokerMapper.getAllPuller(user,puller);
        return new PageBean<List<Map<String, Object>>>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getPages(),page.getResult());
    }
}
