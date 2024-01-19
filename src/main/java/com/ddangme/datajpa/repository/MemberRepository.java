package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
