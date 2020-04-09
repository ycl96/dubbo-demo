package com.ycl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author : YangChunLong
 * @date : Created in 2020/4/5 00:27
 * @description: 控制层 示例
 * @modified By:
 * @version: :
 */
@Controller
@RequestMapping("/v1/demo")
@CrossOrigin
public class DemoController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @RequestMapping("/testServlet")
    public String testServlet (HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        System.out.println(request.getRequestedSessionId());
        if (null != session){
            System.out.println(session.getAttribute("test"));
        }
        session.setAttribute("test","hahha");
        session.setMaxInactiveInterval(20);
        Cookie cookie = new Cookie("ycl","test");
        cookie.setValue("snidng");
        response.addCookie(cookie);
        return "test";
    }
}
