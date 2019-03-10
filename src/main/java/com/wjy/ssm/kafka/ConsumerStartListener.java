package com.wjy.ssm.kafka;

import com.alibaba.fastjson.JSONObject;
import com.wjy.ssm.business.domain.Log;
import com.wjy.ssm.business.mapper.LogMapper;
import com.wjy.ssm.business.service.ILogService;
import com.wjy.ssm.kafka.api.KafkaMessage;
import com.wjy.ssm.kafka.utils.KafkaUtils;
import com.wjy.ssm.util.TimeUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;

/**
 * @author WangJinYi 2019/3/10 0010
 */
public class ConsumerStartListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerStartListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
            new Thread(new ConsumerThread(springContext)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
