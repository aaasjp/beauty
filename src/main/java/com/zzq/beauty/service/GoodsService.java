package com.zzq.beauty.service;

import com.zzq.beauty.model.Goods;
import com.zzq.beauty.util.PageBean;

import java.util.List;

public interface GoodsService {
    void insert(Goods goods);

    void updateGoodsSelective(Goods goods);

    Goods selectByPrimaryKey(Integer id);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param keyWord
     * @return
     */
    PageBean<List<Goods>> goodsList(Integer pageNum,Integer pageSize,String keyWord);



    PageBean<List<Goods>> goodsListWhereInStock(Integer pageNum,Integer pageSize,String keyWord,Integer inStock);
    /**
     * 分页查询下架商品
     * @param pageNum
     * @param pageSize
     * @param keyWord
     * @return
     */
    PageBean<List<Goods>> dropOffGoodsList(Integer pageNum,Integer pageSize,String keyWord);

    /**
     * 上架或下架
     * @param goods
     */
    void shutDownOrUp(Goods goods);

    /**
     * 库存预警
     */
    long goodsInStock(Integer inStock);

}
