package com.example.ajax.dao;

import com.example.ajax.model.Role;
import com.example.ajax.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleHibernateDAO implements RoleDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Role getRoleById(long id) {
        Role role = (Role) entityManager
                .createQuery("SELECT r FROM Role r  WHERE r.id=:id", Role.class)
                .setParameter("id", id)
                .getSingleResult();
        return role;
    }

    public Role getByName(String name) {
        Session currentSession = entityManager.unwrap(Session.class);
        Role role = currentSession.get(Role.class, name);
        return role;
    }
}
