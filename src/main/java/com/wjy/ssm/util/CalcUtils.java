package com.wjy.ssm.util;

import java.math.BigDecimal;

/**
 * Created by liyapeng on 2017/9/18.
 */
public class CalcUtils {


    /**
     * 自由贷1.0 参考PC端sloanCarApplyLoanInfo.js同名方法 王金艺
     * @param time 申请期限
     * @param cusRatio 客户承担利率
     * @param loanMoney 申请贷款金额
     * @return 贴息金额
     * 等额本息
     */
    public static BigDecimal loanCalc1(int time, double cusRatio, double loanMoney) {
        int Z = 1;//还款周期
        int H = 0;//浮动比率
        int T = time / Z;//还款期数（月）
        double HY = Math.round(cusRatio * (1 + H / 100.0) * 10000) / 10000.00;//利率年浮动比率
        double I = HY * Z / 12 / 100.0;//还款周期利率
        double S = loanMoney;   //上期还款后剩余本金
        BigDecimal Interest = new BigDecimal(0); //支付息款
        BigDecimal CLoan = new BigDecimal(0);  //本期还款额
        BigDecimal CInterest;  //本期应还利息
        BigDecimal CorpusPay;  // 本期还款本金
        //第1---(T-1)期
        for (int i = 1; i < T; i++) {
            CInterest = new BigDecimal((S * I * 100))
                    .divide(BigDecimal.valueOf(100), 3, BigDecimal.ROUND_HALF_UP); //本期应还利息, 小数点保留2位
            CLoan = new BigDecimal(loanMoney * I * 100 / (1 - Math.pow(1 + I, -T)))
                    .divide(BigDecimal.valueOf(100), 3, BigDecimal.ROUND_HALF_UP);  //本期还款额,(1+I)的-T次幂运算,四舍五入,小数点保留2位
            CorpusPay = CLoan.subtract(CInterest); // 本期还款本金,四舍五入,小数点保留2位
            S = BigDecimal.valueOf(S).subtract(CorpusPay).doubleValue(); // 本期还款后剩余本金,四舍五入,小数点保留2位
            Interest = Interest.add(CInterest);
        }
        BigDecimal totalPay = CLoan.multiply(BigDecimal.valueOf(T - 1));
        //第T期
        CInterest = new BigDecimal((S * I * 100))
                .divide(BigDecimal.valueOf(100), 3, BigDecimal.ROUND_HALF_UP); //本期应还利息, 小数点保留2位
        CorpusPay = BigDecimal.valueOf(S); // 本期还款本金,四舍五入,小数点保留2位
        CLoan = CorpusPay.add(CInterest);//本期还款额
        S = 0.00; // 本期还款后剩余本金,四舍五入,小数点保留2位
        totalPay = totalPay.add(CLoan);
        Interest = Interest.add(CInterest).setScale(2, BigDecimal.ROUND_HALF_UP);
        return CLoan;
    }

    /**
     * 自由贷1.0 参考PC端sloanCarApplyLoanInfo.js同名方法 王金艺
     * @param time 申请期限
     * @param cusRatio 客户承担利率
     * @param loanMoney 申请贷款金额
     * @return 贴息金额
     * 等额本金
     */
    public static BigDecimal loanCalc2(int time, double cusRatio, double loanMoney) {
        int Z = 1;//还款周期
        int H = 0;//浮动比率
        int T = time / Z;//还款期数（月）
        double HY = Math.round(cusRatio * (1 + H / 100.0) * 10000) / 10000.00;//利率年浮动比率
        double I = HY * Z / 12 / 100.0;//还款周期利率
        double S = loanMoney;   //上期还款后剩余本金
        BigDecimal Interest = new BigDecimal(0); //支付息款
        BigDecimal CLoan = new BigDecimal(0);  //本期还款额
        BigDecimal CInterest;  //本期应还利息
        BigDecimal CorpusPay;  // 本期还款本金
        BigDecimal totalPay = new BigDecimal(0);    // 累计还款总额
        //第1---(T-1)期
        for (int i = 1; i < T; i++) {
            CInterest = new BigDecimal((S * I * 100))
                    .divide(BigDecimal.valueOf(100), 3, BigDecimal.ROUND_HALF_UP); //本期应还利息, 小数点保留2位
            CorpusPay = BigDecimal.valueOf(loanMoney / T); // 本期还款本金,四舍五入,小数点保留2位
            CLoan = CorpusPay.add(CInterest);  //本期还款额,四舍五入,小数点保留2位
            totalPay = totalPay.add(CLoan);
            S = BigDecimal.valueOf(S).subtract(CorpusPay).doubleValue(); // 本期还款后剩余本金,四舍五入,小数点保留2位
            Interest = Interest.add(CInterest);
        }
        //第T期
        CInterest = new BigDecimal((S * I * 100))
                .divide(BigDecimal.valueOf(100), 3, BigDecimal.ROUND_HALF_UP); //本期应还利息, 小数点保留2位
        CorpusPay = BigDecimal.valueOf(S); // 本期还款本金,四舍五入,小数点保留2位
        CLoan = CorpusPay.add(CInterest);//本期还款额
        S = 0.00; // 本期还款后剩余本金,四舍五入,小数点保留2位
        totalPay = totalPay.add(CLoan);
        Interest = Interest.add(CInterest).setScale(2, BigDecimal.ROUND_HALF_UP);
        return CLoan;
    }





}
