package com.zzq.beauty.service;

import com.alibaba.fastjson.JSONObject;
import com.zzq.beauty.model.FlowRecord;
import com.zzq.beauty.model.Statistic;
import com.zzq.beauty.util.PageBean;

import java.util.List;
import java.util.Map;

public interface FlowRecordService {
    void insert(FlowRecord record);

    PageBean<List<Map<String, Object>>> list(Integer pageNum, Integer pageSize, String keyWord, String startDate, String endDate);

    List<Map<String, Object>> listNoPage(String keyWord, String startDate, String endDate);

    double getBetweenTimeAmount(String startDate, String endDate);

    double getBetweenTimeIncome(String startDate, String endDate);

    Statistic statistic(String startDate, String endDate);

}
