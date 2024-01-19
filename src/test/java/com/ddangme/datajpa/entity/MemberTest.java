package com.ddangme.datajpa.entity;

import com.ddangme.datajpa.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired MemberRepository memberRepository;

    @Test
    void testEntity() {
        // Given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        // When
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamA);
        Member member3 = new Member("member3", 10, teamB);
        Member member4 = new Member("member4", 10, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();


        // Then
        List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.print(member);
            System.out.println("-> member.team = " + member.getTeam());
        }
    }

    @DisplayName("JpaEventBaseEntity TEST")
    @Test
    void jpaEventBaseEntityTest() throws Exception{
        // Given
        Member member = new Member("member1");
        memberRepository.save(member);

        Thread.sleep(100);
        member.setUsername("new username");

        em.flush();
        em.clear();

        // When
        Member findMember = memberRepository.findById(member.getId()).get();

        // Then
        System.out.println(findMember.getCreatedDate());
        System.out.println(findMember.getUpdatedDate());
    }
}