package com.wjy.ssm.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjy.ssm.business.domain.Log;
import com.wjy.ssm.business.domain.User;
import com.wjy.ssm.business.mapper.LogMapper;
import com.wjy.ssm.business.service.ILogService;
import com.wjy.ssm.kafka.api.KafkaMessage;
import com.wjy.ssm.kafka.utils.KafkaUtils;
import com.wjy.ssm.util.TimeUtil;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author WangJinYi 2019/3/10 0010
 */
@Service
public class LogServiceImpl implements ILogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

    @Autowired
    private LogMapper logMapper;

    @Override
    public void viewResumeLog(User loginUser) {
        /*Log log = new Log();
        log.setMessage("中国");
        logMapper.insertSelective(log);*/
        KafkaMessage message = new KafkaMessage();
        message.setAccount(loginUser.getAccount());
        message.setTime(LocalDateTime.now());
        message.setMessage("查看了简历");
        try {
            sendToKafka(message);
        } catch (IOException e) {
            LOGGER.error("推送kafka失败", e);
            Log log = new Log();
            log.setAccount(loginUser.getAccount());
            log.setCreateDt(TimeUtil.DATE_FORMATTER_19.format(LocalDateTime.now()));
            log.setMessage("查看了简历");
            logMapper.insertSelective(log);
        }
    }

    private void sendToKafka(KafkaMessage message) throws IOException {
        Producer<String, String> producer = KafkaUtils.getProducer();
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaUtils.LOG_TOPIC,
                JSONObject.toJSONString(message));
        producer.send(record);
        producer.flush();
        producer.close();
    }

}
