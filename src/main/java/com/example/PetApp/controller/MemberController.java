package com.example.PetApp.controller;

import com.example.PetApp.dto.MessageResponse;
import com.example.PetApp.dto.member.*;
import com.example.PetApp.service.member.MemberService;
import com.example.PetApp.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberSignResponseDto> signUp(@ModelAttribute @Valid MemberSignDto memberSignDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(memberSignDto));
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        return memberService.login(loginDto, response);
    }

    @GetMapping("/{memberId}")
    public GetMemberResponseDto getMember(@PathVariable Long memberId, Authentication authentication) {
        return memberService.getMember(memberId, AuthUtil.getEmail(authentication));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestHeader("Authorization") String accessToken) {
        memberService.logout(accessToken);
        return ResponseEntity.ok(new MessageResponse("로그아웃 되었습니다."));
    }

    @GetMapping("/find-id")
    public FindByIdResponseDto findById(@RequestParam String phoneNumber) {
        return memberService.findById(phoneNumber);
    }

    @PostMapping("/send-email")
    public ResponseEntity<MessageResponse> sendEmail(@RequestBody @Valid SendEmailDto sendEmailDto) {
        memberService.sendEmail(sendEmailDto);
        return ResponseEntity.ok(new MessageResponse("인증번호가 이메일로 전송되었습니다."));
    }

    @PostMapping("/verify-code")
    public AccessTokenResponseDto verifyCode(@RequestBody @Valid AuthCodeDto authCodeDto) {
        return memberService.verifyCode(authCodeDto.getEmail(), authCodeDto.getCode());
    }

    @PutMapping("/reset-password")//수정 필요 토큰 있을 때와 없을 때
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody @Valid ResetPasswordDto resetPasswordDto, Authentication authentication) {
        memberService.resetPassword(resetPasswordDto, AuthUtil.getEmail(authentication));
        return ResponseEntity.ok(new MessageResponse("비밀번호가 성공적으로 변경되었습나다."));
    }

    @DeleteMapping()
    public ResponseEntity<MessageResponse> deleteMember(Authentication authentication) {
        memberService.deleteMember(AuthUtil.getEmail(authentication));
        return ResponseEntity.ok(new MessageResponse("탈퇴 되었습니다."));
    }

    @PostMapping("/fcm-token")
    public ResponseEntity<MessageResponse> createFcmToken(@RequestBody @Valid FcmTokenDto fcmTokenDto) {
        memberService.createFcmToken(fcmTokenDto);
        return ResponseEntity.ok(new MessageResponse("fcm토큰 생성완료."));
    }
}
