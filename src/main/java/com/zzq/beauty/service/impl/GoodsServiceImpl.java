package com.zzq.beauty.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzq.beauty.mapper.GoodsMapper;
import com.zzq.beauty.model.Goods;
import com.zzq.beauty.service.GoodsService;
import com.zzq.beauty.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public void insert(Goods goods) {
        goodsMapper.insert(goods);
    }

    @Override
    public void updateGoodsSelective(Goods goods) {
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public Goods selectByPrimaryKey(Integer id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageBean<List<Goods>> goodsList(Integer pageNum, Integer pageSize, String keyWord) {
        PageHelper.startPage(pageNum,pageSize);
        Page<List<Goods>> page = goodsMapper.goodsList(keyWord,null);
        return new PageBean<List<Goods>>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getPages(),page.getResult());
    }

    @Override
    public PageBean<List<Goods>> goodsListWhereInStock(Integer pageNum, Integer pageSize, String keyWord,Integer inStock) {
        PageHelper.startPage(pageNum,pageSize);
        Page<List<Goods>> page = goodsMapper.goodsList(keyWord," and num <="+inStock);
        return new PageBean<List<Goods>>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getPages(),page.getResult());
    }

    @Override
    public void shutDownOrUp(Goods goods) {
        goods.setState(goods.getState()==0?1:0);
        updateGoodsSelective(goods);
    }

    @Override
    public PageBean<List<Goods>> dropOffGoodsList(Integer pageNum, Integer pageSize, String keyWord) {
        PageHelper.startPage(pageNum,pageSize);
        Page<List<Goods>> page = goodsMapper.dropOffGoodsList(keyWord);
        return new PageBean<List<Goods>>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getPages(),page.getResult());
    }

    @Override
    public long goodsInStock(Integer inStock) {
        return goodsMapper.goodsInStock(inStock);
    }
}
