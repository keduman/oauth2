package com.example.oauth2.controller;

import cn.apiclub.captcha.Captcha;
import com.example.oauth2.util.CaptchaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @Value("${captcha.max.tries}")
    private String captchaNoMaxTries;

    private String counterValue = "counter";

    @RequestMapping("/login")
    public String login (ModelMap model, HttpSession session){
        Integer counter = session.getAttribute(counterValue) == null ? 0 : (Integer) session.getAttribute(counterValue);
        if(counter.intValue() >= Integer.parseInt(captchaNoMaxTries)){
        }
        return null;
    }
}
