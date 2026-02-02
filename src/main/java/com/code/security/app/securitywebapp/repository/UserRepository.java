package com.code.security.app.securitywebapp.repository;

import com.code.security.app.securitywebapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>{

    UserEntity findByUsername(String username);
}
