package com.example.homeworkhelper.login;

public class UserDto {
//    public static String AVATAR_DIR="D:\\homework_helper_springboot\\image";
    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_avatar() {
        return u_avatar;
    }

    public void setU_avatar(String u_avatar) {
        this.u_avatar = u_avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    private String u_name="佚名";
    private String u_avatar;//头像怎么存
    private String gender;
    private String role_name;
    private String phone;
    private String grade;
    private String u_id;
    private int role_id;

    @Override
    public String toString() {
        return "UserDto{" +
                "u_name='" + u_name + '\'' +
                ", u_avatar='" + u_avatar + '\'' +
                ", gender='" + gender + '\'' +
                ", role_name='" + role_name + '\'' +
                ", phone='" + phone + '\'' +
                ", grade='" + grade + '\'' +
                ", u_id=" + u_id +
                ", role_id=" + role_id +
                '}';
    }
}

