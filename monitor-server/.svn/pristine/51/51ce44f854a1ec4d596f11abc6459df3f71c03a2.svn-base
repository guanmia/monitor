package com.anyun100.storage.monitor.gateway.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.anyun100.storage.monitor.gateway.service.DomainUserDetailsService;
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
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
                .antMatchers("/resources/**",  "/api/v1/admin/user/forgotpasswordstep1", "/api/v1/admin/user/forgotpasswordstep2" );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/index.html").permitAll().and().logout().deleteCookies("remove").invalidateHttpSession(false).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    @Bean
    public ResourceServerConfigurer resourceServerConfigurer() {
        return new ResourceServerConfigurerAdapter() {
            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                resources.resourceId("blah");
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                // allow anonymous access to health check endpoint
                        .antMatchers("/api/v1/admin/user/forgotpasswordstep1", "/api/v1/admin/user/forgotpasswordstep2", "/index.html", "/", "/login", "/static/**/**").permitAll()
                // everything else requires authentication
                        .anyRequest().authenticated();
            }
        };
    
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
