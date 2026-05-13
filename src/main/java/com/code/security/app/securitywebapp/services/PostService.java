package com.code.security.app.securitywebapp.services;


import com.code.security.app.securitywebapp.dtos.PostDTO;
import com.code.security.app.securitywebapp.entities.PostEntity;
import com.code.security.app.securitywebapp.entities.UserEntity;
import com.code.security.app.securitywebapp.exceptions.ResourceNotFoundException;
import com.code.security.app.securitywebapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    public PostDTO createNewPost(PostDTO inputPost) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    public PostDTO getPostById(Long id){
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        return modelMapper.map(postEntity, PostDTO.class);
    }


}
