package com.zzq.beauty.controller;

import com.mchange.util.impl.CommandLineParserImpl;
import com.mysql.jdbc.StringUtils;
import com.zzq.beauty.constant.CommonConstant;
import com.zzq.beauty.model.*;
import com.zzq.beauty.rest.MyRestResponse;
import com.zzq.beauty.service.*;
import com.zzq.beauty.util.CommonUtil;
import com.zzq.beauty.util.DateUtil;
import com.zzq.beauty.util.PageBean;
import com.zzq.beauty.util.RestCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/careRecord")
public class CareRecordController {
    @Autowired
    private CareRecordService careRecordService;
    @Autowired
    private BuyVipCardService buyVipCardService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private PersonService personService;
    @Autowired
    private FlowRecordService flowRecordService;
    @Autowired
    private BuyGoodsService buyGoodsService;


    @RequestMapping("/add")
    public ModelAndView add(@RequestParam(value = "id") Integer personId) {
        ModelAndView modelAndView = new ModelAndView();
        PageBean<List<Goods>> page = goodsService.goodsList(1, 1000, "%%");
        Person person = personService.getPersonById(personId);
        modelAndView.addObject("userId", personId);
        modelAndView.addObject("person", person);
        modelAndView.addObject("goodsList", page.getList());
        modelAndView.addObject("userList", personService.getSalesman());
        modelAndView.addObject("buyVipCardList", buyVipCardService.getBuyVipCardInUse(personId));
        modelAndView.setViewName("/careRecord/addCareRecord");
        return modelAndView;
    }

    /**
     * @param goodsId  商品IDs
     * @param userId   技师
     * @param personId 客户
     * @return
     */
    @RequestMapping("/save")
    @Transactional
    public @ResponseBody
    MyRestResponse save(@RequestParam(value = "goodsId", required = false) Integer[] goodsId,
                        @RequestParam(value = "num", required = false) Integer[] num,
                        @RequestParam(value = "userId", required = false) Integer userId,
                        @RequestParam(value = "personId", required = false) Integer personId,
                        @RequestParam(value = "serviceDate", required = false) String serviceDate,
                        @RequestParam(value = "amount", required = false) Float amount,
                        @RequestParam(value = "memo", required = false) String memo,
                        @RequestParam(value = "payTypeCode", required = false) String payTypeCode) {
        if (null == goodsId || 0 == goodsId.length) {
            return new MyRestResponse(RestCode._301.getCode(), "没有选择产品！");
        }
        if (null == userId || 0 == userId) {
            return new MyRestResponse(RestCode._301.getCode(), "没有选择技师！");
        }
        if (null == amount) {
            return new MyRestResponse(RestCode._301.getCode(), "没有输入消费金额！");
        }
        if (null == payTypeCode) {
            return new MyRestResponse(RestCode._301.getCode(), "没有选择支付方式！");
        }
        if (StringUtils.isEmptyOrWhitespaceOnly(serviceDate)) {
            return new MyRestResponse(RestCode._301.getCode(), "没有选择服务时间！");
        }
        Person person = personService.getPersonById(personId);
        Person user = personService.getPersonById(userId);
        if (null == person) {
            return new MyRestResponse(RestCode._301.getCode(), "客户不存在！");
        }
        if (null == user) {
            return new MyRestResponse(RestCode._301.getCode(), "技师不存在！");
        }
        if (num == null || goodsId.length != num.length || goodsId.length == 0) {
            return new MyRestResponse(RestCode._301.getCode(), RestCode._301.getMessage());
        }

        String payType = CommonConstant.getPayType(payTypeCode);
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Float income = 0F;
        //String type = "";
        Date servicedate = new Date();
        try {
            if (serviceDate != null || serviceDate.length() > 0)
                servicedate = DateUtil.sdf.parse(serviceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CareRecord careRecord = new CareRecord();
        careRecord.setPersonid(personId);
        careRecord.setUserid(userId);
        careRecord.setServicedate(servicedate);
        careRecord.setCreatedate(new Date());
        careRecord.setAmount(amount);
        careRecord.setMemo(memo);
        careRecord.setPayType(payType);
        Integer operatorId = CommonUtil.getUserIdByRequest();
        Person operator = personService.getPersonByUserId(operatorId);
        careRecord.setOperatorId(operator == null ? null : operator.getId());
        BuyVipCard buyVipCard = null;
        if (payType.equals(CommonConstant.VIPCARD_PAY_TYPE)) {
            careRecord.setBuyVipCardId(Integer.parseInt(payTypeCode));
            buyVipCard = buyVipCardService.selectByPrimaryKey(Integer.parseInt(payTypeCode));
            Float remainder = buyVipCard.getRemainder();
            if (remainder.compareTo(amount) < 0) {
                return new MyRestResponse(RestCode._301.getCode(), "该会员卡可用余额不足！");
            }
            remainder = remainder - amount;
            buyVipCard.setRemainder(remainder);
            buyVipCard.setUpdatedate(new Date());
            income = 0F;//使用会员卡付款，不产生净收入
            buyVipCardService.updateByPrimaryKeySelective(buyVipCard);
        } else {
            income = amount;
        }
        Integer carerecordId = careRecordService.insert(careRecord);

        String re[] = buyGoodsService.buy(goodsId, num, personId, carerecordId);
        if (!re[0].equals("200")) {
            return new MyRestResponse(Integer.parseInt(re[0]), re[1]);
        }

        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setPersonid(personId);
        flowRecord.setUserid(userId);
        flowRecord.setAmount(amount);
        flowRecord.setItemIds(CommonUtil.combineArray(goodsId, ","));
        flowRecord.setIncome(income);
        flowRecord.setOperatorId(operator==null?null:operator.getId());
        flowRecord.setType(CommonConstant.CARE_SERVICE_TYPE);
        flowRecord.setCreatedate(new Date());
        flowRecord.setMemo(memo);
        flowRecord.setPayType(payType);
        flowRecordService.insert(flowRecord);

        return new MyRestResponse(RestCode._200.getCode(), "操作成功！");
    }

    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "startDate", required = false, defaultValue = "null") String startDate,
                             @RequestParam(value = "endDate", required = false, defaultValue = "null") String endDate,
                             @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                             @RequestParam(value = "keyWord", defaultValue = "", required = false) String keyWord) {
        ModelAndView modelAndView = new ModelAndView();
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

        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate", endDate);

        PageBean<List<Map<String, Object>>> page = careRecordService.list(pageNum, 30, "%" + keyWord + "%", startDate, endDate);
        modelAndView.addObject("list", page.getList());
        modelAndView.addObject("page", page);
        modelAndView.addObject("keyWord", keyWord);
        modelAndView.setViewName("/careRecord/careRecordList");
        return modelAndView;
    }
}
