package com.wjy.ssm.service.impl;

import com.wjy.ssm.domain.User;
import com.wjy.ssm.service.ILoginCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangJinYi 2019/3/3 0003
 */
@Service
public class LoginCacheImpl implements ILoginCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCacheImpl.class);

    private static final Map<String, User> LOGIN_USER_CACHE = new HashMap<>();

    @Override
    public void addUser(User user) {
        user.setTime(System.currentTimeMillis());
        LOGIN_USER_CACHE.put(user.getAccount(), user);
        LOGGER.info("用户：" + user.getAccount() + "，登录成功！");
    }

    @Override
    public void removeUser(String account) {
        LOGIN_USER_CACHE.remove(account);
        LOGGER.info("用户：" + account + "，退出登录！");
    }

    @Override
    public User getUser(String account) {
        User user = LOGIN_USER_CACHE.get(account);
        long time = user.getTime();
        if (System.currentTimeMillis() - time > 1000 * 60 * 30) {
            return null;
        } else {
            return user;
        }
    }
}
