package com.example.ajax.service;

import com.example.ajax.dao.RoleDAO;
import com.example.ajax.dao.RoleHibernateDAO;
import com.example.ajax.dao.UserDAO;
import com.example.ajax.model.Role;
import com.example.ajax.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserDetailsService, UserService{
    @Autowired
    private UserDAO userDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleDAO roleDAO;

    @Transactional(readOnly = true)
    public List<User> listAllUsers() throws SQLException {
        return userDAO.listAllUsers();
    }

    @Transactional
    public void updateUser(User user) throws SQLException {
         userDAO.updateUser(user);
    }

    @Transactional
    public void addUser(User user) throws Exception {
        Set<Role> roles = new HashSet<>();
        if (user.getRoles().iterator().next().getName().contains("ADMIN")) {
            roles.add(roleDAO.getRoleById(1L));
        } else if (user.getRoles().iterator().next().getName().contains("USER")) {
            roles.add(roleDAO.getRoleById(2L));
        }
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.addUser(user);
    }
    @Transactional
    public void deleteUser(Long id) throws SQLException {
        userDAO.deleteUser(id);
    }
    @Transactional
    public User getUserById(Long id) throws SQLException {
        return userDAO.getUserById(id);
    }
    @Transactional
    public User findUserByUserName(String userName) throws SQLException {
        return userDAO.findByUsername(userName);
    }
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userDAO.findByUsername(username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
