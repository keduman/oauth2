package com.example.oauth2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.thymeleaf.util.ObjectUtils;

import java.util.Objects;

@Slf4j
public class OAuthTokenConfig extends DefaultTokenServices {
    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication){
        log.info("createAccessToken authentication");
        OAuth2AccessToken token = super.getAccessToken(authentication);
        try {
            if(Objects.isNull(token) || token.isExpired()){
                OAuth2AccessToken auth2AccessToken = super.createAccessToken(authentication);
                return auth2AccessToken;
            }
        } catch (DuplicateKeyException e) {
            log.info("Duplicated key found");
            token = super.getAccessToken(authentication);
            log.info("The token {}", token );
            return token;
        } catch (Exception e){
            log.info(e.getMessage());
        }
        return token;
    }
}
