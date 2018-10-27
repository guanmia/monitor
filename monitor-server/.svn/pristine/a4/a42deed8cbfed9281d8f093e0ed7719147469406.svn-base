package com.anyun100.storage.monitor.oauth2server;

import org.apache.http.HttpStatus;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.anyun100.storage.monitor.oauth2server.service.DomainUserDetailsService;
import com.anyun100.storage.monitor.user_service.domain.UsernameAuditorAware;
import com.anyun100.storage.monitor.user_service.service.UserServiceConfiguration;

@Configuration
@EntityScan({"com.anyun100.storage.monitor.user_service.domain", "com.anyun100.storage.monitor.audit_service.domain"})
@Import({UserServiceConfiguration.class, UsernameAuditorAware.class})
@EnableJpaRepositories({"com.anyun100.storage.monitor.user_service.repository", "com.anyun100.storage.monitor.audit_service.repository"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers(HttpMethod.OPTIONS).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().and().logout().deleteCookies("remove").invalidateHttpSession(false).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
            .and() // 基于Form表单的认证，用户可自定义
				.httpBasic(); // 启用HTTPBasic认证
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()); //可指定从数据库加载用户信息
       /* auth.inMemoryAuthentication()
            .withUser("user")
            .password("user")
            .authorities("READ")
            .and()
            .withUser("admin")
            .password("admin")
            .authorities("READ", "WRITE");*/
    }
    
    @Bean
	public UserDetailsService userDetailsService() {
		return new DomainUserDetailsService();
	}
}
