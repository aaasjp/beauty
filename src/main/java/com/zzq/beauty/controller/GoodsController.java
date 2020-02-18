package com.zzq.beauty.controller;

import com.zzq.beauty.model.Goods;
import com.zzq.beauty.model.GoodsType;
import com.zzq.beauty.rest.MyRestResponse;
import com.zzq.beauty.service.GoodsService;
import com.zzq.beauty.service.GoodsTypeService;
import com.zzq.beauty.util.PageBean;
import com.zzq.beauty.util.RestCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsTypeService goodsTypeService;

    @RequestMapping("/goodsList")
    public ModelAndView goodsList(
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(value = "keyWord", defaultValue = "", required = false) String keyWord,
            @RequestParam(value = "inStock", defaultValue = "0", required = false) Integer inStock
    ) {
        PageBean<List<Goods>> page = null;
        ModelAndView modelAndView = new ModelAndView();
        if (inStock == 0) {
            page = goodsService.goodsList(pageNum, 10, "%" + keyWord + "%");
        } else {
            page = goodsService.goodsListWhereInStock(pageNum, 10, "%" + keyWord + "%", inStock);
        }
        modelAndView.addObject("list", page.getList());
        modelAndView.addObject("page", page);
        modelAndView.addObject("keyWord", keyWord);
        modelAndView.addObject("inStock", inStock);
        modelAndView.setViewName("/goods/goodsList");
        return modelAndView;
    }
    //inStockGoodsList.html

    /**
     * 下架商品
     *
     * @param pageNum
     * @param keyWord
     * @return
     */
    @RequestMapping("/dropOffGoodsList")
    public ModelAndView dropOffGoodsList(
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(value = "keyWord", defaultValue = "", required = false) String keyWord
    ) {

        ModelAndView modelAndView = new ModelAndView();
        PageBean<List<Goods>> page = goodsService.dropOffGoodsList(pageNum, 10, "%" + keyWord + "%");
        modelAndView.addObject("list", page.getList());
        modelAndView.addObject("page", page);
        modelAndView.addObject("keyWord", keyWord);
        modelAndView.setViewName("/goods/shutDownGoodsList");
        return modelAndView;
    }

    @RequestMapping("/addGoods")
    public ModelAndView addGoods(@RequestParam(value = "goodsId", defaultValue = "0", required = false) Integer goodsId) {
        ModelAndView modelAndView = new ModelAndView();
        if (goodsId == null || goodsId == 0) {

        } else {
            modelAndView.addObject("goods", goodsService.selectByPrimaryKey(goodsId));
        }
        List<GoodsType> goodsTypeList = goodsTypeService.goodsTypeList();
        modelAndView.addObject("goodsTypeList", goodsTypeList);
        modelAndView.setViewName("/goods/addGoods");
        return modelAndView;
    }

    @RequestMapping("/saveGoods")
    public @ResponseBody
    MyRestResponse saveGoods(@ModelAttribute Goods goods,
                             @RequestParam(value = "goodsId", required = false) Integer goodsId
    ) {
        if (goodsId == null && goodsId == null) {//新增
            goods.setState(1);
            goods.setCreatedate(new Date());
            goodsService.insert(goods);
            return new MyRestResponse(200, "成功");
        } else {//修改
            goods.setId(goodsId);
            goods.setUpdatedate(new Date());
            goodsService.updateGoodsSelective(goods);
            return new MyRestResponse(RestCode._300.getCode(), RestCode._300.getMessage());
        }
    }

    @RequestMapping("/shutDownOrUp")
    public @ResponseBody
    MyRestResponse shutDownOrUp(@RequestParam(value = "goodsId") Integer goodsId) {
        goodsService.shutDownOrUp(goodsService.selectByPrimaryKey(goodsId));
        return new MyRestResponse(200, "成功");
    }
}
