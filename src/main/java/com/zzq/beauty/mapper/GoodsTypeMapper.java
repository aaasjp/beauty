package com.zzq.beauty.mapper;

import com.zzq.beauty.model.GoodsType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsTypeMapper {
    List<GoodsType> goodsTypeList();
}