package com.bmf.gp.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by felic on 3/28/2016.
 */
@Entity
@Table(name = "sites", schema = "groupproj")
public class SitesEntity implements Serializable {

    private Integer siteId;
    private String siteKey;
    private Set<UsersEntity> users;

    public SitesEntity() {}

    public SitesEntity(Integer siteId, String siteKey) {
        this.siteId = siteId;
        this.siteKey = siteKey;
    }

    @Id
    @Column(name = "site_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @Column(name = "site_key", unique = true, nullable = false, length = 225)
    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    @OneToMany(cascade= CascadeType.ALL, mappedBy="sites")
    public Set<UsersEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersEntity> users) {
        this.users = users;
    }

    public void addUser(UsersEntity user) {
        this.users.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SitesEntity that = (SitesEntity) o;

        if (siteId != null ? !siteId.equals(that.siteId) : that.siteId != null) return false;
        if (siteKey != null ? !siteKey.equals(that.siteKey) : that.siteKey != null) return false;
        return users != null ? users.equals(that.users) : that.users == null;

    }

    @Override
    public int hashCode() {
        int result = siteId != null ? siteId.hashCode() : 0;
        result = 31 * result + (siteKey != null ? siteKey.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SitesEntity{" +
                "siteId=" + siteId +
                ", siteKey='" + siteKey + '\'' +
                ", users=" + users +
                '}';
    }
}
