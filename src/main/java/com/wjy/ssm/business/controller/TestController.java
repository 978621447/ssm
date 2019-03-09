package com.wjy.ssm.business.controller;

import com.wjy.ssm.business.service.ITestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangJinYi
 *         2018/8/22
 */
@Api(tags = "框架测试接口")
@Controller
@RequestMapping("/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ITestService testService;

    @RequestMapping("/index")
    public String index(String s) {
        System.out.println("controller");
        testService.test();
        return "html/index";
    }

    @ApiOperation(value = "map2json", notes = "map2json")
    @RequestMapping(value = "/map", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> map() {
        Map<String, Object> result = new HashMap<>();
        result.put("key1", "value");
        result.put("key2", 132465);
        result.put("key3", null);
        return result;
    }

    @RequestMapping("/dataBase")
    @ResponseBody
    public int dataBase() {
        LOGGER.info("中文不乱码！！！");
        return testService.testDataBase();
    }

    @RequestMapping("/page")
    @ResponseBody
    public Map<String, Object> page() {
        return testService.testPageHelper();
    }
}
