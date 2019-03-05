package com.wjy.ssm.service;

import java.util.Map;

/**
 * @author WangJinYi
 *         2018/8/22
 */
public interface ITestService {

    void test();

    int testDataBase();

    void testTransactional();

    Map<String, Object> testPageHelper();
}
