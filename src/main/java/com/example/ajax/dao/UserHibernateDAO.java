package com.example.ajax.dao;

import com.example.ajax.model.Role;
import com.example.ajax.model.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserHibernateDAO implements UserDAO {
        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public List<User> listAllUsers() {
            return entityManager.
                    createQuery("SELECT distinct u FROM User u LEFT JOIN FETCH u.roles").getResultList();
        }

        @Override
        public void addUser(User user) throws SQLException {
        entityManager.merge(user);
        }

        @Override
        public void updateUser(User user) throws SQLException {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.update(user);
        }

        @Override
        public User getUserById(Long id) {
        User editUser = (User) entityManager
                .createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id=:id", User.class)
                .setParameter("id", id)
                .getSingleResult();
        User userEditById = new User(editUser.getId(), editUser.getUsername(), editUser.getLastName(), editUser.getAge(), editUser.getEmail(), editUser.getPassword(), editUser.getRoles());
        return userEditById;
        }

        @Override
        public Role getRoleById(Long id){
        Role role = (Role) entityManager
                .createQuery("SELECT r FROM Role r  WHERE r.id=:id", Role.class)
                .setParameter("id", id)
                .getSingleResult();
        return role;
        }

        @Override
        public User findByUsername(String username) throws SQLException {
        return  (User)entityManager.createQuery(
                "SELECT u FROM  User u  JOIN FETCH u.roles  WHERE u.userName=:username", User.class).
                setParameter("username", username).setMaxResults(1).getSingleResult();
        }

        @Override
        public void deleteUser(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        User user = currentSession.get(User.class, id);
        currentSession.delete(user);
        }
   }


