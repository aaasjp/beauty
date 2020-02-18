package com.zzq.beauty.controller;

import com.mysql.jdbc.StringUtils;
import com.sun.org.apache.bcel.internal.generic.FLOAD;
import com.zzq.beauty.constant.CommonConstant;
import com.zzq.beauty.model.BuyVipCard;
import com.zzq.beauty.model.FlowRecord;
import com.zzq.beauty.model.Person;
import com.zzq.beauty.model.VipCard;
import com.zzq.beauty.rest.MyRestResponse;
import com.zzq.beauty.service.BuyVipCardService;
import com.zzq.beauty.service.FlowRecordService;
import com.zzq.beauty.service.PersonService;
import com.zzq.beauty.service.VipCardService;
import com.zzq.beauty.util.CommonUtil;
import com.zzq.beauty.util.PageBean;
import com.zzq.beauty.util.RestCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 购买商品页面
 */
@Controller
@RequestMapping("/buyVipCard")
public class BuyVipCardController {
    @Autowired
    private BuyVipCardService buyVipCardService;
    @Autowired
    private VipCardService vipCardService;
    @Autowired
    private PersonService personService;
    @Autowired
    private FlowRecordService flowRecordService;

    @RequestMapping("/buyVipCardPage")
    public ModelAndView buyVipCardPage(@RequestParam(value = "id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", id);
        Person person = personService.getPersonById(id);
        modelAndView.addObject("person", person);
        modelAndView.addObject("userList", personService.getSalesman());
        modelAndView.setViewName("/buyVipCard/buyVipCard");
        return modelAndView;
    }

    //购买会员卡
    @RequestMapping("/buy")
    @Transactional
    public @ResponseBody
    MyRestResponse buy(@RequestParam(value = "vipCardId", required = false) Integer[] vipCardId,
                       @RequestParam(value = "num", required = false) Integer[] num,
                       @RequestParam(value = "personId") Integer personId,
                       @RequestParam(value = "userId") Integer userId,
                       @RequestParam(value = "memo", required = false) String memo,
                       @RequestParam(value = "payTypeCode", required = false) String payTypeCode) {
        /*if (num == null || vipCardId.length != num.length || vipCardId.length == 0) {
            return new MyRestResponse(RestCode._301.getCode(), RestCode._301.getMessage());
        }*/
        if (vipCardId == null) {
            return new MyRestResponse(RestCode._301.getCode(), "请选择会员卡");
        }
        if (vipCardId.length > 1) {
            return new MyRestResponse(RestCode._301.getCode(), "暂只支持一次购买一张会员卡");
        }
        String payType = CommonConstant.getPayType(payTypeCode);
        String[] re = buyVipCardService.buy(vipCardId[0], personId, userId, memo, payType);
        if (re[0].equals("302")) {
            return new MyRestResponse(RestCode._302.getCode(), re[1] + RestCode._302.getMessage());
        }
        if (re[0].equals("301")) {
            return new MyRestResponse(RestCode._301.getCode(), RestCode._301.getMessage());
        }
        Person person = personService.getPersonById(personId);
        person.setType(2);//会员
        personService.updatePersonSelective(person);

        String itemIds = "";
        Float amount = 0F;
        Float income = 0F;
        for (Integer vId : vipCardId) {
            VipCard vipCard = vipCardService.selectByPrimaryKey(vId);
            itemIds += vipCard.getId() + ",";
            amount += vipCard.getPrice();
            income += vipCard.getPrice();
        }
        if (!StringUtils.isNullOrEmpty(itemIds)) {
            itemIds = itemIds.substring(0, itemIds.length() - 1);
        }

        FlowRecord flowRecord = new FlowRecord();

        flowRecord.setPersonid(person.getId());
        flowRecord.setUserid(userId);
        Integer operatorId = CommonUtil.getUserIdByRequest();
        Person operator = personService.getPersonByUserId(operatorId);
        flowRecord.setOperatorId(operator == null ? null : operator.getId());

        flowRecord.setAmount(amount);
        flowRecord.setItemIds(itemIds);
        flowRecord.setIncome(income);
        flowRecord.setMemo(memo);
        flowRecord.setType(CommonConstant.VIPCARD_BUY_FLOW_TYPE);
        flowRecord.setPayType(payType);
        flowRecord.setCreatedate(new Date());
        flowRecordService.insert(flowRecord);
        return new MyRestResponse(RestCode._200.getCode(), "购买成功！");
    }

    /**
     * @return
     */
    @RequestMapping("/buyVipCardList")
    public ModelAndView buyVipCardList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord) {
        ModelAndView modelAndView = new ModelAndView();
        PageBean<List<Map<String, Object>>> page = buyVipCardService.buyVipCardList(pageNum, 10, "%" + keyWord + "%");
        modelAndView.addObject("list", page.getList());
        modelAndView.addObject("page", page);
        modelAndView.addObject("keyWord", keyWord);
        modelAndView.setViewName("/buyVipCard/buyVipCardList");
        return modelAndView;
    }

    //注销会员卡
    @RequestMapping("/end")
    public @ResponseBody
    MyRestResponse end(@RequestParam(value = "id") Integer id) {
        buyVipCardService.end(id);
        return new MyRestResponse(RestCode._200.getCode(), "操作成功！");
    }

    //激活会员卡
    @RequestMapping("/active")
    public @ResponseBody
    MyRestResponse active(@RequestParam(value = "id") Integer id) {
        buyVipCardService.active(id);
        return new MyRestResponse(RestCode._200.getCode(), "操作成功！");
    }

    //会员卡续卡
    @RequestMapping("/renewVipCard")
    public ModelAndView renewVipCard(@RequestParam(value = "id", required = true) Integer buyVipCardId) {
        ModelAndView modelAndView = new ModelAndView();
        BuyVipCard buyVipCard = buyVipCardService.selectByPrimaryKey(buyVipCardId);
        Person person = personService.getPersonById(buyVipCard.getPersonId());
        modelAndView.addObject("userList", personService.getSalesman());
        modelAndView.addObject("person", person);
        modelAndView.addObject("buyVipCard", buyVipCard);
        modelAndView.setViewName("/buyVipCard/renewVipCard");
        return modelAndView;
    }

    //会员卡续卡
    @RequestMapping("/renew")
    @Transactional
    public @ResponseBody
    MyRestResponse renew(@RequestParam(value = "buyVipCardId", required = true) Integer buyVipCardId,
                         @RequestParam(value = "renewAmount", required = true) Float renewAmount,
                         @RequestParam(value = "realOffsetAmount", required = true) Float realOffsetAmount,
                         @RequestParam(value = "userId", required = true) Integer userId,
                         @RequestParam(value = "payTypeCode", required = true) String payTypeCode,
                         @RequestParam(value = "memo", required = false) String memo) {
        if (0 == buyVipCardId) {
            return new MyRestResponse(RestCode._301.getCode(), RestCode._301.getMessage());
        }
        BuyVipCard buyVipCard = buyVipCardService.selectByPrimaryKey(buyVipCardId);
        if (null == buyVipCard) {
            return new MyRestResponse(RestCode._301.getCode(), "用户没有购买会员卡记录");
        }
        Integer operatorId = CommonUtil.getUserIdByRequest();
        Person operator = personService.getPersonByUserId(operatorId);

        Float remainder = buyVipCard.getRemainder();
        remainder += realOffsetAmount;
        buyVipCard.setRemainder(remainder);
        buyVipCard.setUpdatedate(new Date());
        buyVipCard.setType(CommonConstant.VIPCARD_RENEW_FLOW_TYPE);
        buyVipCard.setUserId(userId);
        buyVipCard.setPayType(CommonConstant.getPayType(payTypeCode));
        buyVipCard.setMemo(memo);
        buyVipCard.setOperatorId(operator == null ? null : operator.getId());
        buyVipCardService.updateByPrimaryKeySelective(buyVipCard);

        String itemIds = buyVipCard.getVipCardId().toString();
        Float amount = renewAmount;
        Float income = renewAmount;

        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setPersonid(buyVipCard.getPersonId());
        flowRecord.setUserid(userId);
        flowRecord.setOperatorId(operator == null ? null : operator.getId());
        flowRecord.setType(CommonConstant.VIPCARD_RENEW_FLOW_TYPE);
        flowRecord.setPayType(CommonConstant.getPayType(payTypeCode));
        flowRecord.setMemo(memo);
        flowRecord.setAmount(amount);
        flowRecord.setItemIds(itemIds);
        flowRecord.setIncome(income);
        flowRecord.setCreatedate(new Date());
        flowRecordService.insert(flowRecord);
        return new MyRestResponse(RestCode._200.getCode(), "续卡成功！");
    }
}
