package com.controller;

import com.config.CodeUtils;
import com.entity.User;
import com.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 验证码
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping("image")
    public void imageCode(HttpSession session, HttpServletResponse response) throws IOException {
        //生成验证码
        String code = CodeUtils.generateVerifyCode(4);
        //验证码放入session
        session.setAttribute("code",code);
        //验证码存入图片
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        CodeUtils.outputImage(220,60,os,code);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("register")
    public String register(User user){

        try {
            userService.register(user);
            System.out.println("注册成功");
            return "login";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("注册失败");
            return "register";
        }
    }

    /**
     * 用户退出
     */
    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    /**
     * 用户身份认证
     * @param user
     * @return
     */
    @RequestMapping("login")
    public String login(User user, @RequestParam("code") String code, HttpSession session, Model model){
        String code1 = (String) session.getAttribute("code");
        System.out.println("user-----"+user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try{
            if (code1.equalsIgnoreCase(code)){
                UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
                subject.login(token);
                System.out.println("认证成功");
                return "index";
            }else {
                model.addAttribute("msg","验证码错误");
                System.out.println("验证码错误");
                return "login";
            }
        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){
            System.out.println("密码错误");
            e.printStackTrace();
            return "login";
        }
    }
}
