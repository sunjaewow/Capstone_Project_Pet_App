package com.example.PetApp.service.like;

import com.example.PetApp.domain.Member;
import com.example.PetApp.domain.like.Like;
import com.example.PetApp.domain.post.Post;
import com.example.PetApp.dto.like.LikeResponseDto;
import com.example.PetApp.exception.NotFoundException;
import com.example.PetApp.mapper.LikeMapper;
import com.example.PetApp.repository.jpa.*;
import com.example.PetApp.util.SendNotificationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor//like를 superclass로 둠으로써 likeId 겹칠일이없음. 코드 100줄이상 줄임. ㄷㄷ
public class LikeServiceImpl implements LikeService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final SendNotificationUtil sendNotificationUtil;


    @Transactional(readOnly = true)
    @Override
    public LikeResponseDto getLikes(Long postId) {
        log.info("getLikes 요청 postId : {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시물은 없습니다."));
        return LikeMapper.toLikeResponseDto(post.getLikes());
    }

    @Transactional
    @Override
    public ResponseEntity<String> createAndDeleteLike(Long postId, String email) {
        log.info("createAndDeleteLike 요청 postId : {}", postId);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("회원이 존재하지 않습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시물은 없습니다."));
        Optional<Like> existingLike = post.getLikes().stream()
                .filter(like -> like.getMember().equals(member))
                .findFirst();
        return existingLike
                .map(this::deleteLike)
                .orElseGet(() -> createLike(post, member));
    }

    private ResponseEntity<String> deleteLike(Like like) {
        log.info("좋아요 삭제");
        likeRepository.delete(like);
        return ResponseEntity.ok("좋아요 삭제했습니다.");
    }

    private ResponseEntity<String> createLike(Post post, Member member) {
        log.info("좋아요 생성");
        Like like = Like.builder()
                .member(member)
                .post(post)
                .build();
        post.getLikes().add(like);
        likeRepository.save(like);

        sendNotification(post, member);

        return ResponseEntity.status(HttpStatus.CREATED).body("좋아요 생성했습니다.");
    }

    private void sendNotification(Post post, Member member) {
        String message = member.getName() + "님이 회원님의 게시물을 좋아합니다.";
        sendNotificationUtil.sendNotification(post.getMember(), message);
    }


}
