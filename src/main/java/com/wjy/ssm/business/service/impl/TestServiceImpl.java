package com.wjy.ssm.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjy.ssm.business.mapper.TestMapper;
import com.wjy.ssm.util.BusinessException;
import com.wjy.ssm.business.service.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangJinYi
 *         2018/8/22
 */
@Service
public class TestServiceImpl implements ITestService{

    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private TestMapper testMapper;

    @Override
    public void test() {
        System.out.println("test======================");
    }

    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public int testDataBase() {
        LOGGER.debug("log4j -- debug");
        LOGGER.info("log4j -- info");
        LOGGER.warn("log4j -- warn");
        LOGGER.error("log4j -- error");
        return testMapper.test();
    }

    @Override
    public void testTransactional() {
        testMapper.testTransaction();
        throw new BusinessException("testTransactional");
    }

    @Override
    public Map<String, Object> testPageHelper() {
        Map<String, Object> result = new HashMap<>();
        PageHelper.startPage(2, 2);
        List<Map<String, Object>> data = testMapper.testPageHelper();
        PageInfo<Map<String, Object>> pageData = new PageInfo<>(data);
        result.put("data", pageData);
        return result;
    }

}
