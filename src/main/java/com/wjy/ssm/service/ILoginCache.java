package com.wjy.ssm.service;

import com.wjy.ssm.domain.User;

/**
 * @author WangJinYi 2019/3/3 0003
 */
public interface ILoginCache {

    String COOKIE_TOKEN_NAME = "token";

    void addUser(User user);

    void removeUser(String account);

    User getUser(String account);
}
