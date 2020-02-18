package com.zzq.beauty.beetl;

import com.alibaba.fastjson.JSONObject;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.stereotype.Component;

@Component
public class VipCardJSONFunction implements Function {
    @Override
    public Object call(Object[] objects, Context context) {
        if(objects.length<=1||objects.length>2){
            return "参数错误！";
        }
        String jsonObject = objects[0].toString();
        JSONObject goods = JSONObject.parseObject(jsonObject);
        String str="";
        switch (objects[1].toString()){
            case "1":
                str= "会员卡名称："+goods.get("name")+"  |   价格："+goods.get("price");
                break;
            case "2":
                str= "[快照: "+goods.get("name")+"]";
                break;
        }
        return str;
    }
}
