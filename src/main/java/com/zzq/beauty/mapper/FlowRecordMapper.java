package com.zzq.beauty.mapper;

import com.github.pagehelper.Page;
import com.zzq.beauty.model.FlowRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FlowRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowRecord record);

    int insertSelective(FlowRecord record);

    FlowRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowRecord record);

    int updateByPrimaryKey(FlowRecord record);

    Page<List<Map<String, Object>>> list(@Param("keyWord") String keyWord, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<Map<String, Object>> listNoPage(@Param("keyWord") String keyWord, @Param("startDate") String startDate, @Param("endDate") String endDate);

    long getBetweenTimeCount(@Param("startDate") String startDate, @Param("endDate") String endDate);

    double getBetweenTimeAmount(@Param("startDate") String startDate, @Param("endDate") String endDate);

    double getBetweenTimeIncome(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<FlowRecord> getFlowRecordBetweenTime(@Param("startDate") String startDate, @Param("endDate") String endDate);

}