package com.dogpamines.dogseek.auth.handler;

import com.dogpamines.dogseek.auth.model.DetailsUser;
import com.dogpamines.dogseek.auth.model.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private DetailsService detailsService;  // detailsService 필드 주입

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken loginToken = (UsernamePasswordAuthenticationToken) authentication;

        String id = loginToken.getName();
        String pass = (String) loginToken.getCredentials();

        DetailsUser detailsUser = (DetailsUser) detailsService.loadUserByUsername(id);

        if (!bCryptPasswordEncoder.matches(pass, detailsUser.getPassword())) {
            throw new BadCredentialsException(pass + "는 틀린 비밀번호입니다.");
        }

        return new UsernamePasswordAuthenticationToken(detailsUser, pass, detailsUser.getAuthorities());    // 권한 정보를 반환
    }

    @Override
    public boolean supports(Class<?> authentication) {  // token 타입에 따라서 언제 provider를 사용할지 조건을 지정하는 메소드

        return authentication.equals(UsernamePasswordAuthenticationToken.class);    // UsernamePasswordAuthenticationToken 과 일치할때
    }
}
