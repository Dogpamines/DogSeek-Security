package com.dogpamines.dogseek.user.model.dao;

import com.dogpamines.dogseek.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDTO findByUsername(String userId);

    int regist(UserDTO user);
}
