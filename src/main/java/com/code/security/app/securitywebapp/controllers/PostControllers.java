package com.code.security.app.securitywebapp.controllers;

import com.code.security.app.securitywebapp.dtos.PostDTO;
import com.code.security.app.securitywebapp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SecurityApp")
@RequiredArgsConstructor
public class PostControllers {

    private final PostService postService;

    @Secured({"ROLE_SECURITY_USER", "ROLE_SECURITY_ADMIN"}) // Only users with SECURITY_USER role should be able to view all posts
    @GetMapping("/posts")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{postId}")
    @PreAuthorize("hasAnyRole('SECURITY_USER','SECURITY_ADMIN') AND hasAnyAuthority('POST_VIEW')") // Using PreAuthorize we can also add expressions using authority and roles
    public PostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/posts")
    public PostDTO createNewPost(@RequestBody PostDTO inputPost) {
        return postService.createNewPost(inputPost);
    }


}
