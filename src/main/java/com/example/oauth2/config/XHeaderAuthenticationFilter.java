package com.example.oauth2.config;

import com.example.oauth2.enumaration.StaticIpEnum;
import com.example.oauth2.service.OauthLoginUnauthorizedIpService;
import com.example.oauth2.service.OauthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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

@Component
@Slf4j
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

            if(sampleSystemControl || control){
                filterChain.doFilter(request, response);
            } else {
                oauthLoginUnauthorizedIpService.save(ip);
                log.error("Unauthorized ip: {}", ip);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized log-in");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean systemControl(boolean control, boolean sampleSystemControl, String ip) {
        if(!sampleSystemControl){
            if(!StringUtils.isEmpty(ip)){
                control = false;
                String[] ipArray = ip.split(",");
                for (String ipValue : ipArray){
                    if(checkIp(ipValue.trim()) || StaticIpEnum.IP_LIST.getIpBlock().contains(ipValue.trim()) || oauthService.checkIp(ipValue.trim())){
                        control = true;
                        break;
                    }
                }
            }
        }
        return control;
    }

    private boolean checkIp(String ip) {
        SubnetUtils.SubnetInfo subnet24 = (new SubnetUtils(sample24BitIPBlock, sample24BitIPBlockMask)).getInfo();
        SubnetUtils.SubnetInfo subnet20 = (new SubnetUtils(sample20BitIPBlock, sample20BitIPBlockMask)).getInfo();
        SubnetUtils.SubnetInfo subnet16 = (new SubnetUtils(sample16BitIPBlock, sample16BitIPBlockMask)).getInfo();

        return subnet24.isInRange(ip) || subnet20.isInRange(ip) || subnet16.isInRange(ip);
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
