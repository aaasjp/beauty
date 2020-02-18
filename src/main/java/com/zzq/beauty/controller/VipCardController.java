package com.zzq.beauty.controller;

import com.zzq.beauty.model.VipCard;
import com.zzq.beauty.rest.MyRestResponse;
import com.zzq.beauty.service.VipCardService;
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
@RequestMapping("/vipCard")
public class VipCardController {
    @Autowired
    private VipCardService vipCardService;
    @RequestMapping("/vipCardList")
    public ModelAndView vipCardList(
            @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
            @RequestParam(value = "keyWord",defaultValue = "",required = false) String keyWord,
            @RequestParam(value = "inStock",defaultValue = "0",required = false) Integer inStock
    ){
        PageBean<List<VipCard>>page =null;
        ModelAndView modelAndView = new ModelAndView();
        if(inStock==0){
            page=vipCardService.vipCardList(pageNum,10,"%"+keyWord+"%");
        }else{
            page=vipCardService.vipCardListWhereInStock(pageNum,10,"%"+keyWord+"%",inStock);
        }

        modelAndView.addObject("list",page.getList());
        modelAndView.addObject("page",page);
        modelAndView.addObject("keyWord",keyWord);
        modelAndView.addObject("inStock",inStock);
        modelAndView.setViewName("/vipCard/vipCardList");
        return modelAndView;
    }

    /**
     * 下架会员卡
     * @param pageNum
     * @param keyWord
     * @return
     */
    @RequestMapping("/dropOffVipCardList")
    public ModelAndView dropOffVipCardList(
            @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
            @RequestParam(value = "keyWord",defaultValue = "",required = false) String keyWord
    ){

        ModelAndView modelAndView = new ModelAndView();
        PageBean<List<VipCard>>page =vipCardService.dropOffVipCardList(pageNum,10,"%"+keyWord+"%");
        modelAndView.addObject("list",page.getList());
        modelAndView.addObject("page",page);
        modelAndView.addObject("keyWord",keyWord);
        modelAndView.setViewName("/vipCard/shutDownVipCardList");
        return modelAndView;
    }

    @RequestMapping("/addVipCard")
    public ModelAndView addVipCard(@RequestParam(value = "vipCardId",defaultValue = "0",required = false)Integer vipCardId){
        ModelAndView modelAndView = new ModelAndView();
        if(vipCardId==null||vipCardId==0){

        }else{
            modelAndView.addObject("vipCard",vipCardService.selectByPrimaryKey(vipCardId));
        }
        modelAndView.setViewName("/vipCard/addVipCard");
        return modelAndView;
    }
    @RequestMapping("/saveVipCard")
    public @ResponseBody
    MyRestResponse saveVipCard(@ModelAttribute VipCard vipCard,
                             @RequestParam(value = "vipCardId",required = false) Integer vipCardId
    ){
        if(vipCardId==null&&vipCardId==null){//新增
            vipCard.setState(1);
            vipCard.setCreatedate(new Date());
            vipCardService.insert(vipCard);
            return new MyRestResponse(200,"成功");
        }else{//修改
            vipCard.setId(vipCardId);
            vipCard.setUpdatedate(new Date());
            vipCardService.updateVipCardSelective(vipCard);
            return new MyRestResponse(RestCode._300.getCode(),RestCode._300.getMessage());
        }
    }

    @RequestMapping("/shutDownOrUp")
    public  @ResponseBody MyRestResponse shutDownOrUp(@RequestParam(value = "vipCardId") Integer vipCardId){
        vipCardService.shutDownOrUp(vipCardService.selectByPrimaryKey(vipCardId));
        return new MyRestResponse(200,"成功");
    }
}
