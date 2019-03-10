package com.wjy.ssm.kafka;

import com.alibaba.fastjson.JSONObject;
import com.wjy.ssm.business.domain.Log;
import com.wjy.ssm.business.mapper.LogMapper;
import com.wjy.ssm.kafka.api.KafkaMessage;
import com.wjy.ssm.kafka.utils.KafkaUtils;
import com.wjy.ssm.util.TimeUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;

/**
 * @author WangJinYi 2019/3/10 0010
 */
public class ConsumerThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerThread.class);

    private WebApplicationContext springContext;

    public ConsumerThread(WebApplicationContext springContext) {
        this.springContext = springContext;
    }

    @Override
    public void run() {
        try {
            Consumer<String, String> consumer = KafkaUtils.getConsumer();
            consumer.subscribe(Collections.singleton(KafkaUtils.LOG_TOPIC));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                try {
                    for (ConsumerRecord<String, String> record : records) {
                        KafkaMessage message = JSONObject
                                .parseObject(record.value(), KafkaMessage.class);
                        saveLog(message);
                    }
                } catch (Exception e) {
                    LOGGER.error("解析KafkaMessage数据异常");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLog(KafkaMessage message) {
        LogMapper logMapper = springContext.getBean(LogMapper.class);
        Log log = new Log();
        log.setAccount(message.getAccount());
        log.setCreateDt(TimeUtil.DATE_FORMATTER_19.format(message.getTime()));
        log.setMessage(message.getMessage());
        logMapper.insertSelective(log);
    }
}
