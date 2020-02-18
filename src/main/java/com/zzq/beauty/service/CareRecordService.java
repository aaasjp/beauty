package com.zzq.beauty.service;

import com.zzq.beauty.model.CareRecord;
import com.zzq.beauty.util.PageBean;

import java.util.List;
import java.util.Map;

public interface CareRecordService {
    Integer insert(CareRecord record);

    PageBean<List<Map<String,Object>>> list(Integer pageNum,Integer pageSize,String keyWord,String startDate,String endDate);

    /**
     * 一段时间内 护理次数
     * @param startDate
     * @param endDate
     * @return
     */
    long getBetweenTimeCount(String startDate,String endDate);

    String lastCareDate(Integer personId);

    CareRecord selectByPrimaryKey(Integer id);

}
