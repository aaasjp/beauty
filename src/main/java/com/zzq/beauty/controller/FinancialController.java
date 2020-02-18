package com.zzq.beauty.controller;

import com.github.pagehelper.Page;
import com.mysql.jdbc.StringUtils;
import com.zzq.beauty.constant.CommonConstant;
import com.zzq.beauty.model.Goods;
import com.zzq.beauty.model.VipCard;
import com.zzq.beauty.service.*;
import com.zzq.beauty.util.DateUtil;
import com.zzq.beauty.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuzhiqiang on 2018/4/22.
 * 财务管理
 */
@Controller
@RequestMapping("/financial")
public class FinancialController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private FlowRecordService flowRecordService;
    @Autowired
    private PersonService personService;
    @Autowired
    private VipCardService vipCardService;

    /**
     * 销售记录
     *
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param keyWord
     * @return
     */
    @RequestMapping("/sales")
    public ModelAndView sales(@RequestParam(value = "startDate", required = false, defaultValue = "null") String startDate,
                              @RequestParam(value = "endDate", required = false, defaultValue = "null") String endDate,
                              @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                              @RequestParam(value = "keyWord", defaultValue = "", required = false) String keyWord
    ) {
        if (startDate.equals("null")) {
            startDate = new SimpleDateFormat("yyyy-MM-01").format(new Date());
        }
        if (endDate.equals("null")) {
            int maxDay = DateUtil.getCurrentMonthLastDay();
            endDate = new SimpleDateFormat("yyyy-MM-" + maxDay).format(new Date());
        }
        if (!startDate.contains(CommonConstant.startTimeOfHMS))
            startDate += CommonConstant.startTimeOfHMS;
        if (!endDate.contains(CommonConstant.endTimeOfHMS))
            endDate += CommonConstant.endTimeOfHMS;

        List<Map<String, Object>> flowRecordList = flowRecordService.listNoPage("%" + keyWord + "%", startDate, endDate);
        double sumAmount = 0;
        double sumIncome = 0;
        for (Map<String, Object> map : flowRecordList) {
            double amount = Double.parseDouble(map.getOrDefault("amount", 0).toString());
            double income = Double.parseDouble(map.getOrDefault("income", 0).toString());
            sumAmount += amount;
            sumIncome += income;
        }
        ModelAndView modelAndView = new ModelAndView();
        PageBean<List<Map<String, Object>>> pageBean = flowRecordService.list(pageNum, 30, "%" + keyWord + "%", startDate, endDate);
        Page page = (Page) pageBean.getList();
        List<HashMap<String, Object>> mapList = page.getResult();
        for (Map map : mapList) {
            String production = "";
            String itemIds = map.get("itemIds").toString();
            if (!StringUtils.isNullOrEmpty(itemIds)) {
                String items[] = itemIds.split(",");
                String type = map.get("type").toString();
                if (type.equals(CommonConstant.CARE_SERVICE_TYPE)) {
                    for (String itemId : items) {
                        Goods goods = goodsService.selectByPrimaryKey(Integer.parseInt(itemId));
                        production += "【" + goods.getName() + "】,";
                    }
                } else {
                    for (String itemId : items) {
                        Integer vipCardId = Integer.parseInt(itemId);
                        VipCard vipCard = vipCardService.selectByPrimaryKey(vipCardId);
                        production += "【" + vipCard.getName() + "】,";
                    }
                }
            }
            if (!StringUtils.isNullOrEmpty(production)) {
                production = production.substring(0, production.length() - 1);
            }
            map.put("production", production);
        }
        modelAndView.addObject("list", pageBean.getList());
        modelAndView.addObject("page", pageBean);
        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate", endDate);
        modelAndView.addObject("keyWord", keyWord);
        modelAndView.addObject("sumAmount", sumAmount);
        modelAndView.addObject("sumIncome", sumIncome);
        modelAndView.setViewName("/financial/financialList");
        return modelAndView;
    }

    /**
     * 统计
     * 当月护理人 （次数）  总次数
     * 销售额
     * 新增会员
     * 产品销量（top10）
     */
    public ModelAndView count(
            @RequestParam(value = "startDate", required = false, defaultValue = "null") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "null") String endDate
    ) {
        if (startDate.equals("null")) {
            startDate = new SimpleDateFormat("yyyy-MM-01").format(new Date());
        }
        if (endDate.equals("null")) {
            int maxDay = DateUtil.getCurrentMonthLastDay();
            endDate = new SimpleDateFormat("yyyy-MM-" + maxDay).format(new Date());
        }
        //销售额
        //double sale =buyGoodsService.getSale(startDate,endDate);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/");
        //// TODO: 2018/4/22
        return modelAndView;


    }
}
