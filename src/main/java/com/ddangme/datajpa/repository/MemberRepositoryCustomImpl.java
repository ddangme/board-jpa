package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final EntityManager em;
    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }
}
