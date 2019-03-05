package com.wjy.ssm.util;

/**
 * Created by liyapeng on 2017/9/20.
 */
public class ConvertUtil {

    public static String splitString( String str){

        String[] strArry= str.split("\\.");
        return  strArry[0];

    }


}
