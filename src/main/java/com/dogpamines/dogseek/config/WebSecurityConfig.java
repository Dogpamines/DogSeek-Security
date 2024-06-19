package com.dogpamines.dogseek.config;

import com.dogpamines.dogseek.auth.handler.CustomAuthenticationProvider;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    /* 정적 자원에 대한 이증된 사용자의 접근을 설정하는 메소드 */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /* Security filter chain 설정 메소드 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(basic -> basic.disable());

        return http.build();
    }

    private Filter customAuthenticationFilter() {
        return null;
    }

    private Filter jwtAuthorizationFilter() {
        return null;
    }

    /* Authentication의 인증 메소드를 제공하는 매니저(= Provider의 인터페이스)를 반환하는 메소드 */
    @Bean
    public AuthenticationManager authenticationManager() {  // authenticationManager은 authenticationProvider가 필요함
        return new ProviderManager((AuthenticationProvider) customAuthenticationProvider()); // authenticationProvider는 제공되지만 custom 할 것
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    /* 비밀번호를 암호화하는 인코더를 반환하는 메소드 */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

