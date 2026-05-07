package com.code.security.app.securitywebapp.repository;

import com.code.security.app.securitywebapp.entities.SessionEntity;
import com.code.security.app.securitywebapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    List<SessionEntity> findAllByUser(UserEntity user);
}
