package com.dogpamines.dogseek.config;

import com.dogpamines.dogseek.auth.filter.CustomAuthenticationFilter;
import com.dogpamines.dogseek.auth.handler.CustomAuthSuccessHandler;
import com.dogpamines.dogseek.auth.handler.CustomAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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

    /* 사용자 요청(request) 시 수행되는 메소드 */
    private JwtAuthorizationFilter jwtAuthorizationFilter() {   // jwtAuthorizationFilter은 authenticationManager가 필요함
        return new JwtAuthorizationFilter(AuthenticationManager());
    }

    /* Authentication의 인증 메소드를 제공하는 매니저(= Provider의 인터페이스)를 반환하는 메소드 */
    @Bean
    public AuthenticationManager authenticationManager() {  // authenticationManager은 authenticationProvider가 필요함
        return new ProviderManager(CustomAuthenticationProvider()); // authenticationProvider는 제공되지만 custom 할 것
    }

    /* 사용자의 인증 요청을 가로채서 로그인 로직을 수행하는 필터를 반환하는 메소드 */
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthLoginSuccessHandler());
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthLoginFailureHandler());
        return customAuthenticationFilter;
    }


    /* 사용자 정보가 맞을 경우 (=로그인 성공 시) 수행하는 핸들러를 반환하는 메소드 */
    private CustomAuthSuccessHandler customAuthLoginSuccessHandler() {
        return new CustomAuthSuccessHandler();
    }

    private AuthenticationFailureHandler customAuthLoginFailureHandler() {
        return null;
    }
}
