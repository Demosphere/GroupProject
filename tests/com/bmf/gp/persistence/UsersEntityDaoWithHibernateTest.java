package com.bmf.gp.persistence;

import com.bmf.gp.entity.UsersEntity;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by felic on 2/7/2016.
 */
public class UsersEntityDaoWithHibernateTest {

    @Test
    public void testGetAllUsers() throws Exception {

        UsersEntityDaoWithHibernate dao = new UsersEntityDaoWithHibernate();
        List<UsersEntity> users = dao.getAllUsers();

        assertTrue("There is the wrong amount in the list", users.size() > 0);
    }

    @Test
    public void testGetUser() throws Exception {

        UsersEntityDaoWithHibernate dao = new UsersEntityDaoWithHibernate();
        UsersEntity user = dao.getUser(1);

        assertNotNull("Could not get user", user);
    }

    @Test
    public void testUpdatePerson() throws Exception {

        UsersEntityDaoWithHibernate dao = new UsersEntityDaoWithHibernate();
        UsersEntity user = new UsersEntity();
        user.setUserId(1);
        user.setUserName("AtomGirl");
        user.setPassword("password1");
        user.setUserRole("admin");

        dao.updateUser(user);
        assertEquals("This is the wrong user", "AtomGirl", user.getUserName());
    }

    @Test
    public void testDeleteUser() throws Exception {

        UsersEntityDaoWithHibernate dao = new UsersEntityDaoWithHibernate();
        UsersEntity user = new UsersEntity();
        int sizeBefore;
        int sizeAfter;
        user.setUserId(3);
        sizeBefore = dao.getAllUsers().size();
        dao.deleteUser(user);
        sizeAfter = dao.getAllUsers().size();

        assertTrue("The user was not deleted", sizeBefore > sizeAfter);
    }

    @Test
    public void testAddUser() throws Exception {

        UsersEntityDaoWithHibernate dao = new UsersEntityDaoWithHibernate();
        int insertUserId = 0;

        //create user to add
        UsersEntity user = new UsersEntity();
        user.setUserName("Steel");
        user.setPassword("password1");
        user.setUserRole("admin");

        insertUserId = dao.addUser(user);

        assertTrue(insertUserId > 0);
    }
}