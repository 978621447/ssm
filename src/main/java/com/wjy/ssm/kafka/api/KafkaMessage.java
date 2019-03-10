package com.wjy.ssm.kafka.api;

import java.time.LocalDateTime;

/**
 * @author WangJinYi 2019/3/10 0010
 */
public class KafkaMessage {

    /**
     * 用户账号
     */
    private String account;

    /**
     * 当前时间
     */
    private LocalDateTime time;

    /**
     * 时间描述
     */
    private String message;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
