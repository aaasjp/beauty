package com.zzq.beauty.mapper;

import com.github.pagehelper.Page;
import com.zzq.beauty.model.CareRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
@Repository
public interface CareRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CareRecord record);

    int insertSelective(CareRecord record);

    CareRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CareRecord record);

    int updateByPrimaryKey(CareRecord record);

    Page<List<Map<String,Object>>> list(@Param("keyWord") String keyWord,@Param("startDate") String startDate,@Param("endDate") String endDate);

    long getBetweenTimeCount(@Param("startDate") String startDate,@Param("endDate") String endDate);

    @Select("SELECT DATE_FORMAT(serviceDate,'%Y-%m-%d %H:%m:%s') FROM carerecord WHERE personId=#{personId} ORDER BY serviceDate DESC LIMIT 0,1")
    String lastCareDate(@Param("personId") Integer personId);
}