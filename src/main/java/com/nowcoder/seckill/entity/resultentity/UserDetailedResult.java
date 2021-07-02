package com.nowcoder.seckill.entity.resultentity;

public class UserDetailedResult extends UserResult{

    private Integer id;


    private String phone;


    private String username;


    private String nickname;


    private Byte gender;


    private Integer age;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }


    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }


    public Byte getGender() {
        return gender;
    }


    public void setGender(Byte gender) {
        this.gender = gender;
    }


    public Integer getAge() {
        return age;
    }


    public void setAge(Integer age) {
        this.age = age;
    }
}
