package com.zzq.beauty.service;

import com.zzq.beauty.model.VipCard;
import com.zzq.beauty.util.PageBean;

import java.util.List;

public interface VipCardService {
    void insert(VipCard vipCard);

    void updateVipCardSelective(VipCard vipCard);

    VipCard selectByPrimaryKey(Integer id);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param keyWord
     * @return
     */
    PageBean<List<VipCard>> vipCardList(Integer pageNum, Integer pageSize, String keyWord);

    PageBean<List<VipCard>> vipCardListWhereInStock(Integer pageNum, Integer pageSize, String keyWord, Integer inStock);
    /**
     * 分页查询下架商品
     * @param pageNum
     * @param pageSize
     * @param keyWord
     * @return
     */
    PageBean<List<VipCard>> dropOffVipCardList(Integer pageNum, Integer pageSize, String keyWord);

    /**
     * 上架或下架
     * @param vipCard
     */
    void shutDownOrUp(VipCard vipCard);

    /**
     * 库存预警
     */
    long vipCardInStock(Integer inStock);

}
