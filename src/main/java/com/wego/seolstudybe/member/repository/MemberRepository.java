package com.wego.seolstudybe.member.repository;

import com.wego.seolstudybe.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmail(String email);
}
