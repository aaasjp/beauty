package com.zzq.beauty.mapper;

import com.zzq.beauty.model.Sys;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SysMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sys record);

    int insertSelective(Sys record);

    Sys selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sys record);

    int updateByPrimaryKey(Sys record);

    @Select("select * from sys where `key`=#{key} LIMIT 0,1")
    Sys selectByKey(@Param("key") String key);
}