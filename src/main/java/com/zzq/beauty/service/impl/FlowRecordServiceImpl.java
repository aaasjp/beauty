package com.zzq.beauty.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.StringUtils;
import com.zzq.beauty.constant.CommonConstant;
import com.zzq.beauty.mapper.FlowRecordMapper;
import com.zzq.beauty.model.FlowRecord;
import com.zzq.beauty.model.Person;
import com.zzq.beauty.model.Statistic;
import com.zzq.beauty.service.FlowRecordService;
import com.zzq.beauty.service.PersonService;
import com.zzq.beauty.util.DateUtil;
import com.zzq.beauty.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FlowRecordServiceImpl implements FlowRecordService {
    @Autowired
    FlowRecordMapper flowRecordMapper;
    @Autowired
    PersonService personService;

    @Override
    @Transactional
    public void insert(FlowRecord record) {
        flowRecordMapper.insert(record);

        Person person = personService.getPersonById(record.getPersonid());
        Person pp = new Person();
        pp.setId(person.getId());
        pp.setLastCareDate(record.getCreatedate());
        personService.updatePersonSelective(pp);
    }

    @Override
    public PageBean<List<Map<String, Object>>> list(Integer pageNum, Integer pageSize, String keyWord, String startDate, String endDate) {
        PageHelper.startPage(pageNum, pageSize);
        Page<List<Map<String, Object>>> page = flowRecordMapper.list(keyWord, startDate, endDate);
        return new PageBean<List<Map<String, Object>>>(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages(), page.getResult());
    }

    @Override
    public List<Map<String, Object>> listNoPage(String keyWord, String startDate, String endDate) {
        return flowRecordMapper.listNoPage(keyWord, startDate, endDate);
    }


    @Override
    public double getBetweenTimeAmount(String startDate, String endDate) {
        return flowRecordMapper.getBetweenTimeAmount(startDate, endDate);
    }

    @Override
    public double getBetweenTimeIncome(String startDate, String endDate) {
        return flowRecordMapper.getBetweenTimeIncome(startDate, endDate);
    }

    @Override
    public Statistic statistic(String startDate, String endDate) {
        Float cashSumOfKaidan = 0F;
        Float vipCardSumOfKaidan = 0F;
        Float cashSumOfBanka = 0F;
        Float cashSumOfXuka = 0F;
        Float totalCash = 0F;
        Float totalVipCard = 0F;
        Integer sumOfnewCustomer = 0;
        Integer sumOfoldCustomer = 0;
        Set<Integer> newCustomerSet = new HashSet<>();
        Set<Integer> oldCustomerSet = new HashSet<>();
        List<FlowRecord> flowRecordList = flowRecordMapper.getFlowRecordBetweenTime(startDate, endDate);
        for (FlowRecord flowRecord : flowRecordList) {
            Float amount = flowRecord.getAmount();
            String type = flowRecord.getType();
            String payType = flowRecord.getPayType();
            if (type.equals(CommonConstant.CARE_SERVICE_TYPE)) {//服务开单
                if (payType.equals(CommonConstant.VIPCARD_PAY_TYPE)) {//划卡
                    vipCardSumOfKaidan += amount;
                } else {//现金（包括微信支付宝现金团购等）
                    cashSumOfKaidan += amount;
                }
            }
            if (type.equals(CommonConstant.VIPCARD_BUY_FLOW_TYPE)) {//办卡
                cashSumOfBanka += amount;
            }
            if (type.equals(CommonConstant.VIPCARD_RENEW_FLOW_TYPE)) {//续卡
                cashSumOfXuka += amount;
            }

            Integer personId = flowRecord.getPersonid();
            Person person = personService.getPersonById(personId);
            Date personCreateDate = person.getCreatedate();
            String personCreateDateStr = DateUtil.convertDateToString(personCreateDate);
            if (personCreateDateStr.compareTo(startDate) >= 0) {
                newCustomerSet.add(personId);
            } else {
                oldCustomerSet.add(personId);
            }
        }
        totalCash = cashSumOfBanka + cashSumOfXuka + cashSumOfKaidan;
        totalVipCard = vipCardSumOfKaidan;
        sumOfnewCustomer = newCustomerSet.size();
        sumOfoldCustomer = oldCustomerSet.size();

        Statistic result = new Statistic();
        result.setCashSumOfBanka(cashSumOfBanka);
        result.setCashSumOfKaidan(cashSumOfKaidan);
        result.setCashSumOfXuka(cashSumOfXuka);
        result.setVipCardSumOfKaidan(vipCardSumOfKaidan);
        result.setTotalCash(totalCash);
        result.setTotalVipCard(totalVipCard);
        result.setSumOfnewCustomer(sumOfnewCustomer);
        result.setSumOfoldCustomer(sumOfoldCustomer);
        return result;
    }


}
