package com.example.oauth2.controller;

import com.example.oauth2.enumaration.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/setSession/{value}")
    public void loginCheck(HttpSession session, @PathVariable("value") String value ){ session.setAttribute("captchaAns", value); }

    @GetMapping("/user/me")
    public Principal user(Principal principal){
        log.info("user {},", principal.getName());
        return checkRole(principal, RoleEnum.ROLE_SAMPLE_SYSTEM);
    }

    private Principal checkRole(Principal principal, RoleEnum... roleEnums) {
        for(RoleEnum roleEnum : roleEnums){
            OAuth2Authentication authentication = (OAuth2Authentication) principal;
            if(authentication.getAuthorities().stream().anyMatch(item -> item.getAuthority().equals(roleEnum.name()))){
                return principal;
            }
        }
        return null;
    }

    @RequestMapping("/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response){
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null){
            String tokenValue = authHeader.replace("Bearer","").trim();
            OAuth2AccessToken auth2AccessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(auth2AccessToken);
        }
        new SecurityContextLogoutHandler().logout(request, null, null);
        if(response != null){
            try {
                response.sendRedirect(request.getHeader("referer"));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
