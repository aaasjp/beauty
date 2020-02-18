package com.zzq.beauty.mapper;

import com.github.pagehelper.Page;
import com.zzq.beauty.model.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    Page<List<Goods>> goodsList(@Param("keyWord") String keyWord,@Param("where") String where);

    Page<List<Goods>> dropOffGoodsList(String keyWord);

    void reduceInventory(@Param("num") Integer num,@Param("id") Integer id);
    @Select("select count(*) from goods where num<=#{inStock}")
    long goodsInStock(@Param("inStock") Integer inStock);
}