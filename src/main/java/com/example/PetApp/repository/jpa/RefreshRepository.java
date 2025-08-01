package com.example.PetApp.repository.jpa;

import com.example.PetApp.domain.Member;
import com.example.PetApp.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByMemberMemberId(Long memberId);


    Optional<RefreshToken> findByMember(Member memberId);


    Optional<RefreshToken> findByMemberMemberId(Long memberId);

}
