package com.example.heybuddy.Models;

public class Users {
    private String profilepic;
    private String userPhoneNumber;
    private String userName;

    private String userId;
    private String userEmail;
    private String password;
    private String userStatus;



    public Users(String profilepic, String userPhoneNumber, String userName, String userId, String userEmail, String password, String userStatus) {
        this.profilepic = profilepic;
        this.userPhoneNumber = userPhoneNumber;
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.password = password;
        this.userStatus = userStatus;
    }

    public Users() {
    }

    // SignUp Constructor
    public Users(String userPhoneNumber, String userName, String userEmail, String password, String userid) {
        this.userPhoneNumber = userPhoneNumber;
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.userId = userid;
    }


    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserId(String key) {
        return userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
}
