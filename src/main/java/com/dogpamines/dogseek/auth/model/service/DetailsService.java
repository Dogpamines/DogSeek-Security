package com.dogpamines.dogseek.auth.model.service;

import com.dogpamines.dogseek.auth.model.DetailsUser;
import com.dogpamines.dogseek.user.model.dto.UserDTO;
import com.dogpamines.dogseek.user.model.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DetailsService implements UserDetailsService {

    private final UserService userService;

    public DetailsService(UserService userService) {
        this.userService = userService;
    }

    /* 로그인 요청 시 사용자의 id를 받아 DB에서 사용자 정보를 가져오는 메소드 */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        UserDTO login = userService.findByUsername(username);

        System.out.println("login = " + login);

        if (Objects.isNull(login)) {
            throw new UsernameNotFoundException("해당하는 회원 정보가 존재하지 않습니다.");
        }

        return new DetailsUser(login);
    }
}
