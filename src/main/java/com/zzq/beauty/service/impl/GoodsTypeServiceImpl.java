package com.zzq.beauty.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzq.beauty.mapper.GoodsMapper;
import com.zzq.beauty.mapper.GoodsTypeMapper;
import com.zzq.beauty.model.Goods;
import com.zzq.beauty.model.GoodsType;
import com.zzq.beauty.service.GoodsService;
import com.zzq.beauty.service.GoodsTypeService;
import com.zzq.beauty.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    GoodsTypeMapper goodsTypeMapper;

    @Override
    public List<GoodsType> goodsTypeList() {
        return goodsTypeMapper.goodsTypeList();
    }
}
