package com.zzq.beauty.mapper;

import com.github.pagehelper.Page;
import com.zzq.beauty.model.BuyVipCard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BuyVipCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyVipCard record);

    int insertSelective(BuyVipCard record);

    BuyVipCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyVipCard record);

    int updateByPrimaryKey(BuyVipCard record);

    Page<List<Map<String,Object>>> buyVipCardList(String keyWord);

    List<Map<String,Object>> getBuyVipCardInUse(Integer userId);

    List<Map<String, Object>> getCareBuyVipCard(String vipCardIds);

    Page<List<Map<String,Object>>> getSales(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("keyWord") String keyWord);

    double getSale(@Param("startDate") String startDate, @Param("endDate") String endDate);

    long getSaleVipCardNum(@Param("startDate") String startDate, @Param("endDate") String endDate);
}