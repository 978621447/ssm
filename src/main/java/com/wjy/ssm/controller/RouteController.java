package com.wjy.ssm.controller;

import com.wjy.ssm.domain.User;
import com.wjy.ssm.service.ILoginCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author WangJinYi 2019/3/6 0006
 */
@Controller
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private ILoginCache loginCache;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/resume")
    public String resume(HttpServletRequest request) {
        User loginUser = getLoginUser(request);
        if (loginUser == null) {
            return "noLogin";
        }
        return "resume";
    }

    private User getLoginUser(HttpServletRequest request) {
        User loginUser = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ILoginCache.COOKIE_TOKEN_NAME.equals(cookie.getName())) {
                    String account = cookie.getValue();
                    loginUser = loginCache.getUser(account);
                    break;
                }
            }
        }
        return loginUser;
    }
}
