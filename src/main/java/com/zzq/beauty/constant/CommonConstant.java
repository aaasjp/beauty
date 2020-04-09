package com.zzq.beauty.constant;

import com.mysql.jdbc.StringUtils;

public class CommonConstant {
    public static String UNKNOWN_PAY_TYPE = "未知支付方式";
    public static String CASH_PAY_TYPE = "现金";
    public static String POS_PAY_TYPE = "pos机刷卡";
    public static String ZHIFUBAO_PAY_TYPE = "支付宝";
    public static String WEIXIN_PAY_TYPE = "微信";
    public static String TUANGOU_PAY_TYPE = "团购";
    public static String OTHER_PAY_TYPE = "其他";
    public static String VIPCARD_PAY_TYPE = "会员卡";

    //public static String NON_VIPCARD_FLOW_TYPE = "非划卡付款";
    public static String CARE_SERVICE_TYPE = "开单";
    public static String VIPCARD_RENEW_FLOW_TYPE = "续卡";
    public static String VIPCARD_BUY_FLOW_TYPE = "办卡";

    public static String getPayType(String payTypeCode) {
        if (StringUtils.isNullOrEmpty(payTypeCode)) {
            return CommonConstant.UNKNOWN_PAY_TYPE;
        }
        if (payTypeCode.equals("zhifubao")) {
            return CommonConstant.ZHIFUBAO_PAY_TYPE;
        } else if (payTypeCode.equals("weixin")) {
            return CommonConstant.WEIXIN_PAY_TYPE;
        } else if (payTypeCode.equals("pos")) {
            return CommonConstant.POS_PAY_TYPE;
        } else if (payTypeCode.equals("cash")) {
            return CommonConstant.CASH_PAY_TYPE;
        } else if (payTypeCode.equals("other")) {
            return CommonConstant.OTHER_PAY_TYPE;
        } else if (payTypeCode.equals("tuangou")) {
            return CommonConstant.TUANGOU_PAY_TYPE;
        } else {
            return CommonConstant.VIPCARD_PAY_TYPE;
        }
    }


    public static String startTimeOfHMS = " 00:00:00";
    public static String endTimeOfHMS = " 23:59:59";

}
