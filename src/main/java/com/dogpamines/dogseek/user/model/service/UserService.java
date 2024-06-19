package com.dogpamines.dogseek.user.model.service;

import com.dogpamines.dogseek.common.UserRole;
import com.dogpamines.dogseek.user.model.dao.UserMapper;
import com.dogpamines.dogseek.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO findByUsername(String username) {

        UserDTO login = userMapper.findByUsername(username);

        if (!Objects.isNull(login)) {
            return login;
        } else {
            return null;
        }
    }

    public String signUp(UserDTO user) {

        UserRole userRole = user.getUserAuth();
        System.out.println("여기?");

        if (userRole == null || userRole.name().isEmpty()) {
            return "회원 가입 실패: userRole이 설정되지 않았습니다.";   // 확인용으로만
        }

        user.setUserPass(bCryptPasswordEncoder.encode(user.getUserPass()));
        System.out.println("여기 오나?");

        try {
            UserRole role = UserRole.valueOf(userRole.name().toUpperCase());
            System.out.println("role = " + role);
            user.setUserAuth(role);
            System.out.println("여기 오나??");
        } catch (IllegalArgumentException e) {
            return "회원 가입 실패: 올바르지 않은 사용자 역할입니다.";
        }

        System.out.println("user = " + user);
        int result = userMapper.regist(user);
        System.out.println("result = " + result);

        if (result == 1) {
            return "회원 가입 성공";
        } else {
            return "회원 가입 실패";
        }
    }
}
