package com.example.PetApp.controller;

import com.example.PetApp.dto.MessageResponse;
import com.example.PetApp.dto.post.CreatePostResponseDto;
import com.example.PetApp.dto.post.GetPostResponseDto;
import com.example.PetApp.dto.post.PostDto;
import com.example.PetApp.dto.post.PostResponseDto;
import com.example.PetApp.service.post.normal.NormalPostService;
import com.example.PetApp.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final NormalPostService normalPostService;

    @GetMapping()
    public List<PostResponseDto> getPosts(@RequestParam(defaultValue = "0") int page, Authentication authentication) {
        return normalPostService.getPosts(page, AuthUtil.getEmail(authentication));
    }

    @PostMapping()
    public CreatePostResponseDto createPost(@ModelAttribute @Valid PostDto createPostDto, Authentication authentication) {
        return normalPostService.createPost(createPostDto, AuthUtil.getEmail(authentication));
    }

    @GetMapping("/{postId}")//요청시 댓글까지 한번에 반환. 상세게시물을 보면 무조건 댓글까지 보이게 할거임 그리고 댓글수가 많지않은 커뮤니티라 판단함.
    public GetPostResponseDto getPost(@PathVariable Long postId, Authentication authentication) {
        return normalPostService.getPost(postId, AuthUtil.getEmail(authentication));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable Long postId, Authentication authentication) {
        normalPostService.deletePost(postId, AuthUtil.getEmail(authentication));
        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<MessageResponse> updatePost(@PathVariable Long postId, @ModelAttribute @Valid PostDto postDto, Authentication authentication) {
        normalPostService.updatePost(postId, postDto, AuthUtil.getEmail(authentication));
        return ResponseEntity.ok(new MessageResponse("수정 되었습니다."));
    }
}
