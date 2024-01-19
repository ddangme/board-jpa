package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.domain.Member;
import org.junit.jupiter.api.Disabled;
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
    @Disabled
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
    @Disabled
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
    @Disabled
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
    @Disabled
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
    @Disabled
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

    @DisplayName("findByUsernameAndAgeGreaterThan TEST")
    @Test
    void findByUsernameAndAgeGreaterThan() {
        // Given
        Member member1 = new Member("member", 10);
        Member member2 = new Member("member", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // When
        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("member", 15);

        // Then
        assertThat(result.get(0).getUsername()).isEqualTo("member");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @DisplayName("namedQuery TEST")
    @Test
    void namedQueryTest() {
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsername("member1");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }

    @DisplayName("페이징 TEST")
    @Test
    void paging() {
        // Given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;
        // When
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        // 페이지  계산 공식 코드
        // totalPage = totalCount / size ...
        // 마지막 페이지 ...
        // 최초 페이지 ...

        // Then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);
    }

    @DisplayName("벌크성 수정 쿼리 TEST")
    @Test
    void bulkAgePlusTest() {
        // Given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 19));
        memberJpaRepository.save(new Member("member3", 20));
        memberJpaRepository.save(new Member("member4", 21));
        memberJpaRepository.save(new Member("member5", 40));

        // When
        int resultCount = memberJpaRepository.bulkAgePlus(20);

        // Then
        assertThat(resultCount).isEqualTo(3);
    }

}