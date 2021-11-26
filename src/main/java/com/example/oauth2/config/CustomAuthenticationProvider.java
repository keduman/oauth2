package com.example.oauth2.config;

import cn.apiclub.captcha.Captcha;
import com.example.oauth2.entity.LoginErrorEnum;
import com.example.oauth2.entity.OauthLog;
import com.example.oauth2.service.OauthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OauthService oauthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String remoteIp = attributes.getRequest().getHeader("X-Forwarded-For");
        List<GrantedAuthority> roles = new ArrayList<>();
        List<String> roleList = new ArrayList<>();

        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(checkCaptcha(attributes)){
            return null;
        }
        if(isLdapRegisteredUser(userName, password, remoteIp)){
            log.info("Login ldap username : {}",userName);
            if(countRole(userName) > 0 ){
                roleList.add("ROLE_SAMPLE");
                roles.add(new SimpleGrantedAuthority("ROLE_SAMPLE"));
            }

            if ( roles.size() > 0 ){
                log.info("Login username: {} roles: {}", userName, String.join(",", roleList));
                oauthService.saveLog(OauthLog.Builder().username(userName).isLoginSuccess(true).remoteAddress(remoteIp).oauthRole(String.join(",", roleList)));
            }else{
                oauthService.saveLog(OauthLog.Builder().username(userName).isLoginSuccess(false).remoteAddress(remoteIp).loginErrorEnum(LoginErrorEnum.USER_NOT_FOUND_ON_SYSTEM));
            }
        }else{
            oauthService.saveLog(OauthLog.Builder().username(userName).isLoginSuccess(false).remoteAddress(remoteIp).loginErrorEnum(LoginErrorEnum.LDAP_PASSWORD_ERROR));
        }
        return null;
    }

    private Integer countRole(String userName) {
        return jdbcTemplate.queryForObject("select * from Roles where username = ?", new Object[]{userName}, Integer.class);
    }

    private boolean isLdapRegisteredUser(String userName, String password, String remoteIp) {
        String ldapBase = getDomainComponentByUserName(userName, remoteIp);
        return ldapAuthenticate(ldapBase, userName, password);
    }

    private boolean ldapAuthenticate(String base, String userName, String password) {
        return ldapTemplate.authenticate(base,"(uid="+userName+")", password);
    }

    private String getDomainComponentByUserName(String userName, String remoteIp) {
        final Filter filter = new EqualsFilter("uid", userName);
        final List<String> result = ldapTemplate.search("",filter.toString(), new AbstractContextMapperExtension());

        if(result.isEmpty()){
            oauthService.saveLog(OauthLog.Builder().username(userName).isLoginSuccess(false).remoteAddress(remoteIp).loginErrorEnum(LoginErrorEnum.LDAP_USERNAME_ERROR));
            throw new BadCredentialsException("invalid domain user");
        }
        return result.get(0);
    }

    private final class AbstractContextMapperExtension extends AbstractContextMapper{

        @Override
        protected Object doMapFromContext(final DirContextOperations dirContextOperations) {
            return dirContextOperations.getNameInNamespace();
        }
    }

    private boolean checkCaptcha(ServletRequestAttributes attributes) {
        if(attributes.getRequest().getSession().getAttribute("captcha") != null){
            Captcha captcha = (Captcha) attributes.getRequest().getSession().getAttribute("captcha");
            if(!attributes.getRequest().getSession().getAttribute("captchaAns").equals(captcha.getAnswer())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
