package com.wjy.ssm.service;

import org.springframework.stereotype.Service;

/**
 * @author WangJinYi 2019/3/1 0001
 */
@Service
public interface IUserService {

    void login(String account, String pwd);

}
