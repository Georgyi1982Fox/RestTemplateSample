package com.example.ajax.dao;

import com.example.ajax.model.Role;
import com.example.ajax.model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    List<User> listAllUsers() throws SQLException;
    void addUser(User user) throws SQLException;
    void updateUser(User user) throws SQLException;
    User findByUsername(String username) throws SQLException;
    void deleteUser(Long id) throws SQLException;
    User getUserById(Long id) throws SQLException;
    Role getRoleById(Long id)throws SQLException;
}
