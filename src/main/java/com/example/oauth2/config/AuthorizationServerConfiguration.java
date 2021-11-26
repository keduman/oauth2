package com.example.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder(){return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();}

    @Bean
    public JdbcClientDetailsService clientDetailsService(){
        new JdbcClientDetailsService(dataSource);
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public TokenStore tokenStore(){ return new JdbcTokenStore(dataSource);    }

    @Bean
    @Transactional
    @Primary
    public DefaultTokenServices tokenServices(){
        OAuthTokenConfig tokenService = new OAuthTokenConfig();
        tokenService.setTokenStore(tokenStore());
        tokenService.setSupportRefreshToken(true);
        return tokenService;
    }

    @Bean
    public ApprovalStore approvalStore(){ return new JdbcApprovalStore(dataSource);    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() { return new JdbcAuthorizationCodeServices(dataSource);    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        clients.withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception{
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endPoints) throws Exception {
        endPoints.tokenServices(tokenServices()).approvalStore(approvalStore()).authorizationCodeServices(authorizationCodeServices()).tokenStore(tokenStore());
    }
}
