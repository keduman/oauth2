package com.example.oauth2.config;

import com.example.oauth2.service.OauthLoginUnauthorizedIpService;
import com.example.oauth2.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XHeaderAuthenticationFilter extends OncePerRequestFilter {

    public static String oauthIpHeaderKey = "X-Forwarded-For";

    @Value("${sample.24Bit.IPBlock}")
    private String sample24BitIPBlock;
    @Value("${sample.24Bit.IPBlock.mask}")
    private String sample24BitIPBlockMask;
    @Value("${sample.20Bit.IPBlock}")
    private String sample20BitIPBlock;
    @Value("${sample.20Bit.IPBlock.mask}")
    private String sample20BitIPBlockMask;
    @Value("${sample.16Bit.IPBlock}")
    private String sample16BitIPBlock;
    @Value("${sample.16Bit.IPBlock.mask}")
    private String sample16BitIPBlockMask;

    @Value("${sample.ipblock.enable}")
    private boolean ipBlockEnable;

    @Value("${sample.system.key}")
    private String oauthSampleSystemKey;
    @Value("${sample.system.value}")
    private String oauthSampleSystemValue;

    @Autowired
    private OauthService oauthService;

    @Autowired
    private OauthLoginUnauthorizedIpService oauthLoginUnauthorizedIpService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(ipBlockEnable){
            boolean control = true;
            boolean sampleSystemControl = false;
            sampleSystemControl = cookieControl(request, sampleSystemControl);
            String ip = request.getHeader(oauthIpHeaderKey);
            control = systemControl(control, sampleSystemControl, ip);
        }
    }

    private boolean systemControl(boolean control, boolean sampleSystemControl, String ip) {
        if(!sampleSystemControl){
            if(!StringUtils.isEmpty(ip)){
                control = false;
                String[] ipArray = ip.split(",");
                for (String ipValue : ipArray){
                }
            }
        }
        return false;
    }

    private boolean cookieControl(HttpServletRequest request, boolean sampleSystemControl) {
        if(request.getCookies() != null && request.getCookies().length >0){
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if(oauthSampleSystemKey.equals(cookie.getName())){
                    String value = cookie.getValue();
                    if(value.equals(oauthSampleSystemValue)){
                        sampleSystemControl = true;
                    }
                }
            }
        }
        return sampleSystemControl;
    }
}
