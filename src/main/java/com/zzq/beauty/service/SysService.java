package com.zzq.beauty.service;

import com.zzq.beauty.model.Sys;

/**
 * Created by zhuzhiqiang on 2018/4/28.
 */
public interface SysService {
    public Sys select(String key);
    public void update(Sys sys);
}
