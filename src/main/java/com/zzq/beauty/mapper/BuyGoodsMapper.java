package com.zzq.beauty.mapper;

import com.github.pagehelper.Page;
import com.zzq.beauty.model.BuyGoods;
import com.zzq.beauty.util.PageBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BuyGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyGoods record);

    int insertSelective(BuyGoods record);

    BuyGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuyGoods record);

    int updateByPrimaryKey(BuyGoods record);

    Page<List<Map<String,Object>>> buyGoodsList(String keyWord);

    List<Map<String,Object>> getBuyGoodsAnEnd(Integer userId);

    List<Map<String, Object>> getCareBuyGoods(List<String> goodsIds);

    Page<List<Map<String,Object>>> getSales(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("keyWord") String keyWord);

    double getSale(@Param("startDate") String startDate,@Param("endDate") String endDate);

    long getSaleGoodsNum(@Param("startDate") String startDate,@Param("endDate") String endDate);

    List<Map<String, Object>> getBuyGoodsByCareId(@Param("carerecordId") Integer carerecordId);
}