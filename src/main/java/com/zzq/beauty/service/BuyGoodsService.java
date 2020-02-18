package com.zzq.beauty.service;

import com.zzq.beauty.model.BuyGoods;
import com.zzq.beauty.util.PageBean;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface BuyGoodsService {
    void insert(BuyGoods buyGoods);

    BuyGoods selectByPrimaryKey(Integer id);

    String[] buy(Integer[] goodsId, Integer[] num, Integer personId,Integer carerecordId);

    PageBean<List<Map<String,Object>>> buyGoodsList(Integer pageNum,Integer pageSize,String keyWord);

    void end(Integer id);

    /**
     * 获取用户所有未使用完的产品
     * @param userId
     * @return
     */
    List<Map<String,Object>> getBuyGoodsAnEnd(Integer userId);

    /**
     * 获取使用的护理产品
     * @param goodsIds
     * @return
     */
    List<Map<String,Object>> getCareBuyGoods(List<String> goodsIds);

    /**
     * 销售记录
     * @param pageNum
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param keyWord
     * @return
     */
    PageBean<List<Map<String,Object>>> getSales(Integer pageNum,Integer pageSize,String startDate,String endDate,String keyWord);

    /**
     * 返回销售额
     * @param startDate
     * @param endDate
     * @return
     */
    double getSale(String startDate,String endDate);

    /**
     * 返回销售个数
     */
    long getSaleGoodsNum(String startDate,String endDate);

    /**
     * 获取使用的护理产品
     * @param carerecordId
     * @return
     */
    List<Map<String,Object>> getBuyGoodsByCareId(Integer carerecordId);
}
