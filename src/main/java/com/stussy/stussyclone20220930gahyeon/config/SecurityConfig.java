package com.stussy.stussyclone20220930gahyeon.config;

import com.stussy.stussyclone20220930gahyeon.security.AuthFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter { //config 클래스는 (서버)설정 @Configuration 달아주기

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override //ctrl+o
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.authorizeRequests()//권한 관련 요청 들어올 시
                .antMatchers("/account/login","/index")//지정한 경로로 요청이 들어오면
                .authenticated()//인증 거치기
                .anyRequest()//다른 요청
                .permitAll()//권한부여
                .and()
                .formLogin()//form태그 이용한 로그인
                .usernameParameter("email")
                .loginPage("/account/login")//지정한 경로의 페이지로 로그인페이지 보내기  get 요청
                .loginProcessingUrl("/account/login")//login service post요청
                .failureHandler(new AuthFailureHandler())
                .defaultSuccessUrl("/index");//로그인 성공시 기본으로 이동되는 경로
    }
}
