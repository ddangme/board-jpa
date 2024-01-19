package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.domain.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
