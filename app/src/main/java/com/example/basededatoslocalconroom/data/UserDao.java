package com.example.basededatoslocalconroom.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    public List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Query("SELECT * FROM user WHERE (first_name LIKE '%' || :name || '%') OR (last_name LIKE '%' || :name || '%') OR (first_name || ' ' || last_name LIKE '%' || :name || '%')")
    public List<User> getUsersByName(String name);

    @Insert
    void insertAll(User... users);

    @Insert
    long insert(User user);

    @Delete
    void delete(User user);

    @Update
    int updateUser(User user);

    @Delete
    int deleteUser(User user);

}
