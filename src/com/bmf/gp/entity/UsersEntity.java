package com.bmf.gp.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by felic on 3/28/2016.
 */
@Entity
@Table(name = "users", schema = "groupproj")
public class UsersEntity implements Serializable {

    private Integer userId;
    private String userName;
    private String password;
    private String userRole;
    private SitesEntity site;

    public UsersEntity() {}

    public UsersEntity(Integer userId, String userName, String password, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    @Id
    @Column(name = "user_id", unique = true, nullable = false, length = 11)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "user_name", unique = true, nullable = false, length = 30)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "password", unique = false, nullable = false, length = 30)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "user_role", unique = false, nullable = false, length = 20)
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="site_id")
    public SitesEntity getSite() {
        return site;
    }

    public void setSite(SitesEntity site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (userRole != null ? !userRole.equals(that.userRole) : that.userRole != null) return false;
        return site != null ? site.equals(that.site) : that.site == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userRole='" + userRole + '\'' +
                ", site=" + site +
                '}';
    }
}
