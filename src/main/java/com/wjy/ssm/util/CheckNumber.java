package com.wjy.ssm.util;

/**
 * Created by lenovo on 2017/9/23.
 */
public class CheckNumber {

    public static boolean checkNumber(String number){
        String reg = "^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,5})?))$";
        if(!(number.matches(reg))){
            return false;
        }
        return true;
    }


    /**
     * 验证输入手机号
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean checkMobile(String mobile){
        String reg = "^[0-9]{11}$";
        System.out.println("手机"+mobile.matches(reg));
        if(!(mobile.matches(reg))){
            return false;
        }
        return true;
    }

}
