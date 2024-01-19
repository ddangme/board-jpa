package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
