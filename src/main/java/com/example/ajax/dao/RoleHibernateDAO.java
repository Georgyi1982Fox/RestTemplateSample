package com.example.ajax.dao;

import com.example.ajax.model.Role;
import com.example.ajax.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleHibernateDAO {
    @Autowired
    private EntityManager entityManager;

    public List<User> findAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r").getResultList();
    }

    public Role getByName(String name) {
        Session currentSession = entityManager.unwrap(Session.class);
        Role role = currentSession.get(Role.class, name);
        return role;
    }
}
