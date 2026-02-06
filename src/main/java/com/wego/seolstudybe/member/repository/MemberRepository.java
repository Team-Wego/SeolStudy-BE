package com.wego.seolstudybe.member.repository;

import com.wego.seolstudybe.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
