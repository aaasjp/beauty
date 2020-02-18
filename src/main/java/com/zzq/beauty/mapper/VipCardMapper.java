package com.zzq.beauty.mapper;

import com.github.pagehelper.Page;
import com.zzq.beauty.model.VipCard;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VipCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipCard record);

    int insertSelective(VipCard record);

    VipCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipCard record);

    int updateByPrimaryKey(VipCard record);

    Page<List<VipCard>> vipCardList(@Param("keyWord") String keyWord, @Param("where") String where);

    Page<List<VipCard>> dropOffVipCardList(String keyWord);

    void reduceInventory(@Param("num") Integer num, @Param("id") Integer id);
    @Select("select count(*) from vipCard where num<=#{inStock}")
    long vipCardInStock(@Param("inStock") Integer inStock);
}