package com.zzq.beauty.service;

import com.zzq.beauty.model.BuyVipCard;
import com.zzq.beauty.util.PageBean;

import java.util.List;
import java.util.Map;

public interface BuyVipCardService {
    void insert(BuyVipCard buyVipCard);

    BuyVipCard selectByPrimaryKey(Integer id);

    String[] buy(Integer[] vipCardId, Integer[] num, Integer personId);

    String[] buy(Integer vipCardId, Integer personId, Integer userId, String memo, String payType);

    PageBean<List<Map<String, Object>>> buyVipCardList(Integer pageNum, Integer pageSize, String keyWord);

    void end(Integer id);

    void active(Integer id);

    /**
     * 获取用户所有未使用完的会员卡
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> getBuyVipCardInUse(Integer userId);

    /**
     * 获取使用的会员卡
     *
     * @param vipCardIds
     * @return
     */
    List<Map<String, Object>> getCareBuyVipCard(String vipCardIds);

    /**
     * 销售记录
     *
     * @param pageNum
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param keyWord
     * @return
     */
    PageBean<List<Map<String, Object>>> getSales(Integer pageNum, Integer pageSize, String startDate, String endDate, String keyWord);

    /**
     * 返回销售额
     *
     * @param startDate
     * @param endDate
     * @return
     */
    double getSale(String startDate, String endDate);

    /**
     * 返回销售个数
     */
    long getSaleVipCardNum(String startDate, String endDate);

    int updateByPrimaryKeySelective(BuyVipCard buyVipCard);
}
