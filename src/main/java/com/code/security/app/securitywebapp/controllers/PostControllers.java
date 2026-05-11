package com.code.security.app.securitywebapp.controllers;

import com.code.security.app.securitywebapp.dtos.PostDTO;
import com.code.security.app.securitywebapp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SecurityApp")
@RequiredArgsConstructor
public class PostControllers {

    private final PostService postService;

    @GetMapping("/posts")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{postId}")
    public PostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/posts")
    public PostDTO createNewPost(@RequestBody PostDTO inputPost) {
        return postService.createNewPost(inputPost);
    }


}
