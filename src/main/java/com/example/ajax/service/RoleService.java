package com.example.ajax.service;

import com.example.ajax.dao.RoleHibernateDAO;
import com.example.ajax.model.Role;
import com.example.ajax.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleHibernateDAO roleHibernateDAO;

    @Transactional(readOnly = true)
    public List<User> findAllRoles() throws SQLException {
        return roleHibernateDAO.findAllRoles();
    }

    @Transactional
    public Role findByName(String name) {
        return roleHibernateDAO.getByName(name);
    }
}
