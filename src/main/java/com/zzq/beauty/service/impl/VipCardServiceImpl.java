package com.zzq.beauty.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzq.beauty.mapper.GoodsMapper;
import com.zzq.beauty.mapper.VipCardMapper;
import com.zzq.beauty.model.Goods;
import com.zzq.beauty.model.VipCard;
import com.zzq.beauty.service.GoodsService;
import com.zzq.beauty.service.VipCardService;
import com.zzq.beauty.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VipCardServiceImpl implements VipCardService{
    @Autowired
    VipCardMapper vipCardMapper;

    @Override
    public void insert(VipCard vipCard) {
        vipCardMapper.insert(vipCard);
    }

    @Override
    public void updateVipCardSelective(VipCard vipCard) {
        vipCardMapper.updateByPrimaryKeySelective(vipCard);
    }

    @Override
    public VipCard selectByPrimaryKey(Integer id) {
        return vipCardMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageBean<List<VipCard>> vipCardList(Integer pageNum, Integer pageSize, String keyWord) {
        PageHelper.startPage(pageNum,pageSize);
        Page<List<VipCard>> page = vipCardMapper.vipCardList(keyWord,null);
        return new PageBean<List<VipCard>>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getPages(),page.getResult());
    }

    @Override
    public PageBean<List<VipCard>> vipCardListWhereInStock(Integer pageNum, Integer pageSize, String keyWord,Integer inStock) {
        PageHelper.startPage(pageNum,pageSize);
        Page<List<VipCard>> page = vipCardMapper.vipCardList(keyWord," and num <="+inStock);
        return new PageBean<List<VipCard>>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getPages(),page.getResult());
    }

    @Override
    public void shutDownOrUp(VipCard vipCard) {
        vipCard.setState(vipCard.getState()==0?1:0);
        updateVipCardSelective(vipCard);
    }

    @Override
    public PageBean<List<VipCard>> dropOffVipCardList(Integer pageNum, Integer pageSize, String keyWord) {
        PageHelper.startPage(pageNum,pageSize);
        Page<List<VipCard>> page = vipCardMapper.dropOffVipCardList(keyWord);
        return new PageBean<List<VipCard>>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getPages(),page.getResult());
    }

    @Override
    public long vipCardInStock(Integer inStock) {
        return vipCardMapper.vipCardInStock(inStock);
    }
}
