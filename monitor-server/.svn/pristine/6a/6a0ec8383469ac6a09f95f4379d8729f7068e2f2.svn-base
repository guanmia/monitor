package com.anyun100.storage.monitor.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

@Configuration
@EnableResourceServer
@SuppressWarnings("SpringJavaAutowiringInspection")
public class OAuth2ResourceConfiguration extends ResourceServerConfigurerAdapter {

    Logger log = LoggerFactory.getLogger(OAuth2ResourceConfiguration.class);

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
    	.antMatchers("/user/forgotpasswordstep1", "/user/forgotpasswordstep2").anonymous()
		.antMatchers("/user/forgotpasswordstep1", "/user/forgotpasswordstep2").permitAll().anyRequest()
		.authenticated().and().csrf().disable();
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        log.info("Configuring ResourceServerSecurityConfigurer ");
        resources.resourceId("users").tokenStore(tokenStore);
    }

    @Autowired
    TokenStore tokenStore;

}
