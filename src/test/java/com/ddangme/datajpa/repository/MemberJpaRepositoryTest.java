package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.domain.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @DisplayName("CREATE 테스트")
    @Test
    void createTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // When
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // Then
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
    }

    @DisplayName("FIND ALL 테스트")
    @Test
    void findAllTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // When
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // Then
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @DisplayName("COUNT 테스트")
    @Test
    void countTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // When
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // Then
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);
    }

    @DisplayName("UPDATE 테스트")
    @Test
    void updateTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // When
        member1.setUsername("update member1");
        member2.setUsername("update member2");

        // Then
        assertThat(member1.getUsername()).isEqualTo("update member1");
        assertThat(member2.getUsername()).isEqualTo("update member2");
    }


    @DisplayName("DELETE 테스트")
    @Test
    void deleteTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // When
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        // Then
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(0);
    }

}