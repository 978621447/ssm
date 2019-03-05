package com.wjy.ssm.controller;

import com.wjy.ssm.service.ILoginCache;
import com.wjy.ssm.util.BusinessException;
import com.wjy.ssm.service.IUserService;
import com.wjy.ssm.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
        try {
            userService.login(account, pwd);
            Cookie cookie = new Cookie(ILoginCache.COOKIE_TOKEN_NAME, account);
            response.addCookie(cookie);
        } catch (BusinessException e) {
            return R.error(e.getMessage());
        } catch (Exception e) {
            return R.error("系统异常，请等待修复！");
        }
        return R.ok();
    }
}
