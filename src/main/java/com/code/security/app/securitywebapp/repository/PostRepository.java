package com.code.security.app.securitywebapp.repository;


import com.code.security.app.securitywebapp.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {




}
