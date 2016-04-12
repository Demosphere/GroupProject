package com.bmf.gp.persistence;

import com.bmf.gp.entity.UsersEntity;

import java.util.List;

/**
 * Created by felic on 4/3/2016.
 */
public interface UsersEntityDao {

    List<UsersEntity> getAllUsers();

    UsersEntity getUser(Integer id);

    void updateUser(UsersEntity user);

    Boolean deleteUser(UsersEntity user);

    int addUser(UsersEntity user);
}
