package com.zzq.beauty.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzq.beauty.constant.CommonConstant;
import com.zzq.beauty.mapper.BuyVipCardMapper;
import com.zzq.beauty.mapper.PersonMapper;
import com.zzq.beauty.mapper.VipCardMapper;
import com.zzq.beauty.model.BuyVipCard;
import com.zzq.beauty.model.Person;
import com.zzq.beauty.model.VipCard;
import com.zzq.beauty.service.BuyVipCardService;
import com.zzq.beauty.service.PersonService;
import com.zzq.beauty.util.CommonUtil;
import com.zzq.beauty.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BuyVipCardServiceImpl implements BuyVipCardService {
    @Autowired
    BuyVipCardMapper buyVipCardMapper;
    @Autowired
    VipCardMapper vipCardMapper;

    @Autowired
    private PersonService personService;

    @Override
    public BuyVipCard selectByPrimaryKey(Integer id) {
        return buyVipCardMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insert(BuyVipCard buyVipCard) {
        buyVipCardMapper.insert(buyVipCard);
    }

    @Override
    @Transactional
    public String[] buy(Integer[] vipCardId, Integer[] num, Integer personId) {
        Date date = new Date();
        for (int i = 0; i < vipCardId.length; i++) {
            VipCard vipCard = vipCardMapper.selectByPrimaryKey(vipCardId[i]);
            if (vipCard.getNum() < num[i]) {//库存不足
                return new String[]{"302", vipCard.getName()};
            }
            if (0 >= num[i]) {//参数错误
                return new String[]{"301", ""};
            }
            BuyVipCard buyVipCard = new BuyVipCard();
            buyVipCard.setNum(num[i]);
            buyVipCard.setPrice(vipCard.getPrice());
            buyVipCard.setAvailable(vipCard.getAvailable());
            buyVipCard.setRemainder(vipCard.getAvailable());//初始的可用余额=实际可用余额
            buyVipCard.setVipCardSnapshot(JSON.toJSON(vipCard).toString());
            buyVipCard.setVipCardId(vipCard.getId());
            buyVipCard.setState(1);
            buyVipCard.setPersonId(personId);
            buyVipCard.setCreatedate(date);
            insert(buyVipCard);
            //减少库存
            vipCardMapper.reduceInventory(num[i], vipCard.getId());
        }
        return new String[]{"200", ""};
    }

    @Override
    @Transactional
    public String[] buy(Integer vipCardId, Integer personId, Integer userId, String memo, String payType) {
        VipCard vipCard = vipCardMapper.selectByPrimaryKey(vipCardId);
        if (vipCard.getNum() < 1) {//库存不足
            return new String[]{"302", vipCard.getName()};
        }
        BuyVipCard buyVipCard = new BuyVipCard();
        buyVipCard.setNum(1);
        buyVipCard.setState(1);
        buyVipCard.setPrice(vipCard.getPrice());
        buyVipCard.setAvailable(vipCard.getAvailable());
        buyVipCard.setRemainder(vipCard.getAvailable());//初始的可用余额=实际可用余额
        buyVipCard.setVipCardSnapshot(JSON.toJSON(vipCard).toString());
        buyVipCard.setVipCardId(vipCard.getId());
        buyVipCard.setPersonId(personId);
        buyVipCard.setUserId(userId);
        Integer operatorId = CommonUtil.getUserIdByRequest();
        Person operator = personService.getPersonByUserId(operatorId);
        buyVipCard.setOperatorId(operator == null ? null : operator.getId());

        buyVipCard.setMemo(memo);
        buyVipCard.setPayType(payType);
        buyVipCard.setType(CommonConstant.VIPCARD_BUY_FLOW_TYPE);
        buyVipCard.setCreatedate(new Date());
        insert(buyVipCard);
        //减少库存
        vipCardMapper.reduceInventory(1, vipCard.getId());
        return new String[]{"200", ""};
    }


    @Override
    public PageBean<List<Map<String, Object>>> buyVipCardList(Integer pageNum, Integer pageSize, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        Page<List<Map<String, Object>>> page = buyVipCardMapper.buyVipCardList(keyWord);
        return new PageBean<List<Map<String, Object>>>(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages(), page.getResult());
    }

    @Override
    public void end(Integer id) {
        BuyVipCard buyVipCard = buyVipCardMapper.selectByPrimaryKey(id);
        buyVipCard.setState(0);
        buyVipCard.setUpdatedate(new Date());
        buyVipCardMapper.updateByPrimaryKeySelective(buyVipCard);
    }

    @Override
    public void active(Integer id) {
        BuyVipCard buyVipCard = buyVipCardMapper.selectByPrimaryKey(id);
        buyVipCard.setState(1);
        buyVipCard.setUpdatedate(new Date());
        buyVipCardMapper.updateByPrimaryKeySelective(buyVipCard);
    }

    @Override
    public List<Map<String, Object>> getBuyVipCardInUse(Integer userId) {
        return buyVipCardMapper.getBuyVipCardInUse(userId);
    }

    @Override
    public List<Map<String, Object>> getCareBuyVipCard(String vipCardIds) {
        return buyVipCardMapper.getCareBuyVipCard(vipCardIds);
    }

    @Override
    public PageBean<List<Map<String, Object>>> getSales(Integer pageNum, Integer pageSize, String startDate, String endDate, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        Page<List<Map<String, Object>>> page = buyVipCardMapper.getSales(startDate, endDate, keyWord);
        return new PageBean<List<Map<String, Object>>>(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages(), page.getResult());
    }

    @Override
    public double getSale(String startDate, String endDat) {
        System.out.println(buyVipCardMapper.getSale(startDate, endDat) + "==================");
        return buyVipCardMapper.getSale(startDate, endDat);
    }

    @Override
    public long getSaleVipCardNum(String startDate, String endDate) {
        return buyVipCardMapper.getSaleVipCardNum(startDate, endDate);
    }

    @Override
    public int updateByPrimaryKeySelective(BuyVipCard buyVipCard) {
        return buyVipCardMapper.updateByPrimaryKeySelective(buyVipCard);
    }
}
