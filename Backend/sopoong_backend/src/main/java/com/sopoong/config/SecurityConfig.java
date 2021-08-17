package com.sopoong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sopoong.filter.JwtAuthenticationFilter;
import com.sopoong.filter.JwtFilter;
import com.sopoong.service.CustomUserService;
import com.sopoong.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private final JwtTokenProvider jwtTokenProvider;
	// 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
//                .csrf().disable() // csrf 보안 토큰 disable처리.
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
//                .and()
//                .authorizeRequests() // 요청에 대한 사용권한 체크
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasRole("USER")
//                .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class);
        
    	http.csrf().disable().cors().disable().authorizeRequests().antMatchers("/api/auth/**", "/v2/api-docs", 
                "/swagger-resources/**",  
                "/swagger-ui.html", 
                "/webjars/**").permitAll().antMatchers(HttpMethod.OPTIONS,"/api/**").permitAll().
        anyRequest().authenticated().and().exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
        and().addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }
	
	
}
