package com.example.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomdatabase.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user where first_name LIKE :firstName")
    User findByName(String firstName);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User users);

    @Delete
    void delete(User user);
}
