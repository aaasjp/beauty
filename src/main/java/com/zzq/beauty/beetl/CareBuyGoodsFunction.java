package com.zzq.beauty.beetl;

import com.alibaba.fastjson.JSONObject;
import com.zzq.beauty.model.BuyVipCard;
import com.zzq.beauty.model.CareRecord;
import com.zzq.beauty.model.Goods;
import com.zzq.beauty.model.VipCard;
import com.zzq.beauty.service.*;
import com.zzq.beauty.util.SpringContextUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.server.InactiveGroupException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 *
 */
@Component
public class CareBuyGoodsFunction implements Function {
    @Autowired
    BuyGoodsService buyGoodsService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    BuyVipCardService buyVipCardService;
    @Autowired
    VipCardService vipCardService;
    @Autowired
    CareRecordService careRecordService;

    @Override
    public Object call(Object[] objects, Context context) {
        if (objects == null) {
            return "参数错误";
        }
        String result = "";
        Integer carerecordId = Integer.parseInt(objects[0].toString());
        Integer which = Integer.parseInt(objects[1].toString());
        if (which == 0) {//产品信息
            List<Map<String, Object>> list = buyGoodsService.getBuyGoodsByCareId(carerecordId);
            for (Map<String, Object> map : list) {
                String goodsId = map.get("goodsId").toString();
                String num = map.get("num").toString();
                String price = map.get("price").toString();
                Goods goods = goodsService.selectByPrimaryKey(Integer.parseInt(goodsId));
                result += "【" + goods.getName() + "[" + price + "*" + num + "件]】";
            }
        }
        if (which == 1) {//会员卡信息
            CareRecord careRecord = careRecordService.selectByPrimaryKey(carerecordId);
            Integer buyVipCardId = careRecord.getBuyVipCardId();
            if (null != buyVipCardId) {
                BuyVipCard buyVipCard = buyVipCardService.selectByPrimaryKey(buyVipCardId);
                Integer vipCardId = buyVipCard.getVipCardId();
                VipCard vipCard = vipCardService.selectByPrimaryKey(vipCardId);
                result += vipCard.getName();
            }
        }

        return result;
    }
}
