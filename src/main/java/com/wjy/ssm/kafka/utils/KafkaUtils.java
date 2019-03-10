package com.wjy.ssm.kafka.utils;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author WangJinYi 2019/3/10 0010
 */
public class KafkaUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaUtils.class);
    public static final String LOG_TOPIC = "log";
    private static final String PRODUCER_PROPS_PATH = "/kafka/producer.properties";
    private static final String CONSUMER_PROPS_PATH = "/kafka/consumer.properties";
    private static Producer<String, String> producer;
    private static Consumer<String, String> consumer;

    public static synchronized Producer<String, String> getProducer() throws IOException{
        try {
            if (producer == null) {
                Properties props = new Properties();
                props.load(KafkaUtils.class.getResourceAsStream(PRODUCER_PROPS_PATH));
                producer = new KafkaProducer<>(props);
            }
            return producer;
        } catch (IOException e) {
            LOGGER.error("初始化kafkaProducer异常", e);
            throw e;
        }
    }

    public static synchronized Consumer<String, String> getConsumer() throws IOException{
        try {
            if (consumer == null) {
                Properties props = new Properties();
                props.load(KafkaUtils.class.getResourceAsStream(CONSUMER_PROPS_PATH));
                consumer = new KafkaConsumer<>(props);
            }
            return consumer;
        } catch (IOException e) {
            LOGGER.error("初始化kafkaConsumer异常", e);
            throw e;
        }
    }
}
