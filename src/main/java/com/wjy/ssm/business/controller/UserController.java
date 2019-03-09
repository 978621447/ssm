package com.wjy.ssm.business.controller;

import com.wjy.ssm.business.service.ILoginCache;
import com.wjy.ssm.util.BusinessException;
import com.wjy.ssm.business.service.IUserService;
import com.wjy.ssm.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WangJinYi 2019/3/2 0002
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping("/login")
    public R login(String account, String pwd, HttpServletResponse response) {
        if (StringUtils.isEmpty(account)) {
            return R.error("账号不能为空");
        }
        if (StringUtils.isEmpty(pwd)) {
            return R.error("密码不能为空！");
        }
        try {
            userService.login(account, pwd);
            Cookie cookie = new Cookie(ILoginCache.COOKIE_TOKEN_NAME, account);
            cookie.setMaxAge(20*60);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (BusinessException e) {
            return R.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("账号注册异常", e);
            return R.error("系统异常，请等待修复！");
        }
        return R.ok();
    }

    @RequestMapping("/signUp")
    public R signUp(String account, String pwd, HttpServletResponse response) {
        if (StringUtils.isEmpty(account)) {
            return R.error("账号不能为空");
        }
        if (StringUtils.isEmpty(pwd)) {
            return R.error("密码不能为空！");
        }
        try {
            userService.signUp(account, pwd);
            return login(account, pwd, response);
        } catch (BusinessException e) {
            return R.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("账号注册异常", e);
            return R.error("系统异常，请等待修复！");
        }
    }
}
