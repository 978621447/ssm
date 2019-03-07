package com.wjy.ssm.service.impl;

import com.wjy.ssm.domain.User;
import com.wjy.ssm.service.ILoginCache;
import com.wjy.ssm.util.BusinessException;
import com.wjy.ssm.mapper.UserMapper;
import com.wjy.ssm.service.IUserService;
import com.wjy.ssm.util.MD5Utils;
import com.wjy.ssm.util.StringUtil;
import com.wjy.ssm.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @Override
    public void signUp(String account, String pwd) {
        checkExistAccount(account);
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        String encodePwd = encodePwd(pwd, salt);
        User user = new User();
        user.setAccount(account);
        user.setPwd(encodePwd);
        user.setPwdSalt(salt);
        user.setCreateDt(TimeUtil.DATE_FORMATTER_19.format(LocalDateTime.now()));
        userMapper.insertSelective(user);
    }

    private void checkExistAccount(String account) {
        User existUser = userMapper.selectByAccount(account);
        if (existUser != null) {
            throw new BusinessException("该账号已存在");
        }
    }

    private boolean truePwd(User user, String pwd) {
        return user.getPwd().equals(encodePwd(pwd, user.getPwdSalt()));
    }

    private String encodePwd(String pwd, String salt) {
        return MD5Utils.encrypt("encode:" + pwd + salt + pwd
                + salt + pwd + pwd + salt + "encode_end");
    }
}
