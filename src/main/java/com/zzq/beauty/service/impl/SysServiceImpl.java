package com.zzq.beauty.service.impl;

import com.zzq.beauty.mapper.SysMapper;
import com.zzq.beauty.model.Sys;
import com.zzq.beauty.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhuzhiqiang on 2018/4/28.
 */
@Service
public class SysServiceImpl implements SysService{
    @Autowired
    private SysMapper sysMapper;

    public Sys select(String key){
        return sysMapper.selectByKey(key);
    }

    @Override
    public void update(Sys sys) {
        sysMapper.updateByPrimaryKeySelective(sys);
    }
}
