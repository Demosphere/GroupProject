package com.bmf.gp.persistence;

import com.bmf.gp.entity.SitesEntity;
import com.bmf.gp.entity.UsersEntity;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class TestForeignKeyAssociation {

    @Test
    public void testForeignKeyAssociation() throws Exception {

        SitesDao dao = new SitesDao();
        int insertedSiteId = 0;

        SitesEntity site = new SitesEntity();
        site.setSiteKey("Test-Site-Key");

        UsersEntity user1 = new UsersEntity();
        user1.setUserName("Luke Cage");
        user1.setPassword("password1");
        user1.setUserRole("admin");

        UsersEntity user2 = new UsersEntity();
        user2.setUserName("Captain America");
        user2.setPassword("password1");
        user2.setUserRole("admin");

        UsersEntity user3 = new UsersEntity();
        user3.setUserName("Black Widow");
        user3.setPassword("password1");
        user3.setUserRole("admin");

        Set<UsersEntity> users = new HashSet<UsersEntity>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        site.setUsers(users);

        insertedSiteId = dao.addSite(site);

        assertTrue(insertedSiteId > 0);
    }
}