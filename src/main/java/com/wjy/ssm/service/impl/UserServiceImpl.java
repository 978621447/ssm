package com.wjy.ssm.service.impl;

import com.wjy.ssm.domain.User;
import com.wjy.ssm.service.ILoginCache;
import com.wjy.ssm.util.BusinessException;
import com.wjy.ssm.mapper.UserMapper;
import com.wjy.ssm.service.IUserService;
import com.wjy.ssm.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WangJinYi 2019/3/2 0002
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ILoginCache loginCache;

    @Override
    public void login(String account, String pwd) {
        User user = userMapper.selectByAccount(account);
        if (user == null) {
            throw new BusinessException("账号不存在！");
        }
        if (!truePwd(user, pwd)) {
            throw new BusinessException("密码错误！");
        }
        loginCache.addUser(user);
    }

    private boolean truePwd(User user, String pwd) {
        return user.getPwd().equals(MD5Utils.encrypt(pwd + user.getPwdSalt()));
    }
}
