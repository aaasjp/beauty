package com.zzq.beauty.beetl;

import com.zzq.beauty.service.CareRecordService;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 最后一次服务时间
 */
@Component
public class LastCareDateFunction implements Function{
    @Autowired
    CareRecordService careRecordService;
    @Override
    public Object call(Object[] objects, Context context) {

        return careRecordService.lastCareDate(Integer.parseInt(objects[0].toString()));
    }
}
