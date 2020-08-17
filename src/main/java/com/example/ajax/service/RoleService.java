package com.example.ajax.service;

import com.example.ajax.dao.RoleDAO;
import com.example.ajax.dao.RoleHibernateDAO;
import com.example.ajax.model.Role;
import com.example.ajax.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class RoleService implements RolService{
    @Autowired
    private RoleDAO roleDAO;

    @Transactional
    @Override
    public Role getRoleById(long id) {
        return roleDAO.getRoleById(id);
    }




}
