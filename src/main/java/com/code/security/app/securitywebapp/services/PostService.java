package com.code.security.app.securitywebapp.services;


import com.code.security.app.securitywebapp.entities.PostEntity;
import com.code.security.app.securitywebapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostEntity getPostById(Long id){
        return postRepository.findById(id).orElse(null);
    }


}
