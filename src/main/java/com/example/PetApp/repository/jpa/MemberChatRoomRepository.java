package com.example.PetApp.repository.jpa;

import com.example.PetApp.domain.Member;
import com.example.PetApp.domain.MemberChatRoom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, Long> {

    List<MemberChatRoom> findAllByMembersContains(Member member);

}
