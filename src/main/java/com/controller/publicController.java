package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class publicController {
    //主页
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    //登录
    @RequestMapping("login")
    public String login(){
        return "login";
    }

    //注册
    @RequestMapping("register")
    public String register(){
        return "register";
    }
}
