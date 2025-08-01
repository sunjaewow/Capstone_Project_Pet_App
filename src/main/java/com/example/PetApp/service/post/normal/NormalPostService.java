package com.example.PetApp.service.post.normal;

import com.example.PetApp.dto.post.CreatePostResponseDto;
import com.example.PetApp.dto.post.GetPostResponseDto;
import com.example.PetApp.dto.post.PostDto;
import com.example.PetApp.dto.post.PostResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NormalPostService {

    List<PostResponseDto> getPosts(int page, String email);

    CreatePostResponseDto createPost(PostDto createPostDto, String email);

    GetPostResponseDto getPost(Long postId, String email);

    void deletePost(Long postId, String email);

    void updatePost(Long postId, PostDto postDto, String email);
}
