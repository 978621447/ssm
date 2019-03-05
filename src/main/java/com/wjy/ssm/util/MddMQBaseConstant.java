package com.wjy.ssm.util;

/**
	 *
	 * @Description(功能描述)    :  MQ基础常量
	 * @author(作者)             :  李迎春
	 * @date (开发日期)          :  2016/12/13  10:32
	 * @param
	 * @param
	 * @return  Object
	 */
public class MddMQBaseConstant {
	
	public static final String MDD_DATA_QUERY="mdd.queue.mddBrowseHisData";
	public static final String MDD_DATA_ESUPDATE="mdd.queue.mddVehCarsourceUpdate";
	public static final String MDD_DATA_ESDEL="mdd.queue.mddVehCarsourceDel";

	//加急审批
	public static final String MDD_WCPay_QUERY="mdd.WCPay.queue.mddQuickCheck";
	//清除订单
	public static final String MDD_WCOrder_QUERY="mdd.WCPay.queue.mddChecckOrderQueue";
	

}
