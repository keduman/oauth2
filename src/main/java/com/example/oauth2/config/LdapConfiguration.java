package com.example.oauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfiguration {

    @Value("${ldap.template.url}") //   ldap://glbtds.samplesystem.pvt:389
    private String ldapUrl;

    @Value("${ldap.template.userDn}")//  uid=samplesystem, ou=Users,DC=SAMPLESYSTEM
    private String ldapUserDn;

    @Value("${ldap.password}") // '{cipher}ERfdkfdfk18674-+sfSFnhrsfbs'
    private String ldapPassword;

    @Bean
    public LdapContextSource contextSource(){
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(ldapUrl);
        ldapContextSource.setUserDn(ldapUserDn);
        ldapContextSource.setPassword(ldapPassword);

        return ldapContextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(){
        LdapTemplate ldapTemplate = new LdapTemplate(contextSource());
        ldapTemplate.setIgnorePartialResultException(true);
        return ldapTemplate;
    }
}
