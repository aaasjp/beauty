package com.zzq.beauty.controller;

import com.zzq.beauty.rest.MyRestResponse;
import com.zzq.beauty.service.BuyGoodsService;
import com.zzq.beauty.service.GoodsService;
import com.zzq.beauty.util.PageBean;
import com.zzq.beauty.util.RestCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 购买商品页面
 */
@Controller
@RequestMapping("/buyGoods")
public class BuyGoodsController {
    @Autowired
    private BuyGoodsService buyGoodsService;
    @Autowired
    private GoodsService goodsService;
    @RequestMapping("/buyGoodsPage")
    public ModelAndView buyGoodsPage(@RequestParam(value = "id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId",id);
        modelAndView.setViewName("/buyGoods/buyGoods");
        return  modelAndView;
    }
    //购买商品
    @RequestMapping("/buy")
    public @ResponseBody
    MyRestResponse buy(@RequestParam(value = "goodsId",required = false) Integer[] goodsId,
                       @RequestParam(value = "num",required = false) Integer[] num,
                       @RequestParam(value = "userId") Integer userId){
        if(num==null||goodsId.length!=num.length||goodsId.length==0){
            return new MyRestResponse(RestCode._301.getCode(),RestCode._301.getMessage());
        }
        String[] re =buyGoodsService.buy(goodsId,num,userId,null);
        if(re[0].equals("302")){
            return new MyRestResponse(RestCode._302.getCode(),re[1]+RestCode._302.getMessage());
        }
        if(re[0].equals("301")){
            return new MyRestResponse(RestCode._301.getCode(),RestCode._301.getMessage());
        }
        return new MyRestResponse(RestCode._200.getCode(),"购买成功！");
    }

    /**
     * @return
     */
    @RequestMapping("/buyGoodsList")
    public ModelAndView buyGoodsList(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "keyWord",required = false,defaultValue = "") String keyWord){
        ModelAndView modelAndView = new ModelAndView();
        PageBean<List<Map<String,Object>>> page=buyGoodsService.buyGoodsList(pageNum,10,"%"+keyWord+"%");
        modelAndView.addObject("list",page.getList());
        modelAndView.addObject("page",page);
        modelAndView.addObject("keyWord",keyWord);
        modelAndView.setViewName("/buyGoods/buyGoodsList");
        return  modelAndView;
    }

    //购买商品
    @RequestMapping("/end")
    public @ResponseBody
    MyRestResponse end(@RequestParam(value = "id") Integer id){
        buyGoodsService.end(id);
        return new MyRestResponse(RestCode._200.getCode(),"操作成功！");
    }
}
