package com.bmf.gp.persistence;

import com.bmf.gp.entity.SitesEntity;
import com.bmf.gp.entity.UsersEntity;
import org.apache.log4j.Logger;
import org.junit.Test;
import java.util.Set;

/**
 * Created by felic on 4/10/2016.
 */
public class TestUsernameAndPassword {

    private final Logger log = Logger.getLogger(this.getClass());

    @Test
    public void testGetUsernameAndPassword() throws Exception {

        SitesDao dao = new SitesDao();
        SitesEntity site = dao.getSiteByKey("87d3e948-efd0-40e2-af79-2d532c390d09");

        Integer siteId = site.getSiteId();

        Set<UsersEntity> users = dao.getSite(siteId).getUsers();

        for (UsersEntity user : users) {
            String username = user.getUserName();
            String password = user.getPassword();

            log.info("username: " + username);
            log.info("password: " + password);
        }
    }
}
