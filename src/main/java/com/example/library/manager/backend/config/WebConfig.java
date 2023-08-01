package com.example.library.manager.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 解决post跨域
 *
 * @author jluzhuwanyuan@163.com
 * @date 2023/8/1
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .ignoringAntMatchers("/user/signup")
                .ignoringAntMatchers("/book/create/books")
                .ignoringAntMatchers("/book/update/books")
                .ignoringAntMatchers("/book/delete/books");
        http.authorizeRequests()
                .antMatchers(
                        "/book/update/books",
                        "/book/delete/books",
                        "/book/create/books",
                        "/user/signup")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
