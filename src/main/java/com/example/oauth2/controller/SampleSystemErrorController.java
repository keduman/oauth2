package com.example.oauth2.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SampleSystemErrorController implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.UNAUTHORIZED.value()){
                return "401";
            }
        }
        return "error";
    }

}