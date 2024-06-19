package com.dogpamines.dogseek.user.model.dto;

import com.dogpamines.dogseek.common.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserDTO {

    private int userCode;
    private String userPlatform;
    private String userId;
    private String userPass;
    private String userNick;
    private String userPhone;
    private Date userSignup;
    private Date userLatest;
    private UserRole userAuth;

    public UserDTO(){}

    public UserDTO(int userCode, String userPlatform, String userId, String userPass, String userNick, String userPhone, Date userSignup, Date userLatest, UserRole userAuth) {
        this.userCode = userCode;
        this.userPlatform = userPlatform;
        this.userId = userId;
        this.userPass = userPass;
        this.userNick = userNick;
        this.userPhone = userPhone;
        this.userSignup = userSignup;
        this.userLatest = userLatest;
        this.userAuth = userAuth;
    }

    public List<String> getRole() {
        if (this.userAuth.getRole().length() > 0) {
            return Arrays.asList(this.userAuth.getRole().split(","));
        }
        return new ArrayList<>();
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getUserPlatform() {
        return userPlatform;
    }

    public void setUserPlatform(String userPlatform) {
        this.userPlatform = userPlatform;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public Date getUserSignup() {
        return userSignup;
    }

    public void setUserSignup(Date userSignup) {
        this.userSignup = userSignup;
    }

    public Date getUserLatest() {
        return userLatest;
    }

    public void setUserLatest(Date userLatest) {
        this.userLatest = userLatest;
    }

    public UserRole getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserRole userAuth) {
        this.userAuth = userAuth;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userCode=" + userCode +
                ", userPlatform='" + userPlatform + '\'' +
                ", userId='" + userId + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userNick='" + userNick + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userSignup=" + userSignup +
                ", userLatest=" + userLatest +
                ", userAuth=" + userAuth +
                '}';
    }
}
