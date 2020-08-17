package com.example.ajax.dao;

import com.example.ajax.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO  {
     Role getRoleById(long id);
}
