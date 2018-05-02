package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

public class User implements Serializable {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userName")
    private String username;

    private String password;

    @Column(name = "nickName")
    private String nickname;

    private String sex;

    private String birth;

    private String education;

    private String avastar;

    private static final long serialVersionUID = 1L;

    /**
     * @return Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return nickName
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * @return birth
     */
    public String getBirth() {
        return birth;
    }

    /**
     * @param birth
     */
    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
    }

    /**
     * @return education
     */
    public String getEducation() {
        return education;
    }

    /**
     * @param education
     */
    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    /**
     * @return avastar
     */
    public String getAvastar() {
        return avastar;
    }

    /**
     * @param avastar
     */
    public void setAvastar(String avastar) {
        this.avastar = avastar == null ? null : avastar.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", nickname=").append(nickname);
        sb.append(", sex=").append(sex);
        sb.append(", birth=").append(birth);
        sb.append(", education=").append(education);
        sb.append(", avastar=").append(avastar);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}