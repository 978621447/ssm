package com.wjy.ssm.service.impl;

import com.wjy.ssm.service.ITestRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author WangJinYi
 *         2018/8/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestRedisServiceImplTest {

    @Autowired
    private ITestRedisService testRedisService;

    @Test
    public void testLink() throws Exception {
        testRedisService.testLink();
    }

    @Test
    public void testPool() throws Exception {
        testRedisService.testPool();
    }

}