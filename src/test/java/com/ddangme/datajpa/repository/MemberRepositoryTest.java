package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.domain.Member;
import com.ddangme.datajpa.domain.Team;
import com.ddangme.datajpa.dto.MemberDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @DisplayName("CREATE 테스트")
    @Test
    @Disabled
    void createTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // When
        memberRepository.save(member1);
        memberRepository.save(member2);

        // Then
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

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
        memberRepository.save(member1);
        memberRepository.save(member2);

        // Then
        List<Member> all = memberRepository.findAll();
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
        memberRepository.save(member1);
        memberRepository.save(member2);

        // Then
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);
    }

    @DisplayName("UPDATE 테스트")
    @Test
    @Disabled
    void updateTest() {
        // Given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

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
        memberRepository.save(member1);
        memberRepository.save(member2);

        // When
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        // Then
        long count = memberRepository.count();
        assertThat(count).isEqualTo(0);
    }


    @DisplayName("findByUsernameAndAgeGreaterThan TEST")
    @Test
    void findByUsernameAndAgeGreaterThan() {
        // Given
        Member member1 = new Member("member", 10);
        Member member2 = new Member("member", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // When
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("member", 15);

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

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("member1");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }

    @DisplayName("Query TEST")
    @Test
    void queryTest() {
        // Given
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // When
        List<Member> result = memberRepository.findUser("member1", 10);

        // Then
        assertThat(result.get(0)).isEqualTo(m1);
    }

    @DisplayName("username List 가져오기")
    @Test
    void findUsernameListTest() {
        // Given
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // When
        List<String> usernames = memberRepository.findUsernameList();

        // Then
        for (String username : usernames) {
            System.out.println(username);
        }
    }


    @DisplayName("dto List 가져오기")
    @Test
    void findMemberDtoTest() {
        // Given
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        m1.setTeam(teamA);
        m2.setTeam(teamB);
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // When
        List<MemberDto> findDto = memberRepository.findMemberDto();

        // Then
        for (MemberDto memberDto : findDto) {
            System.out.println(memberDto);
        }
    }

    @DisplayName("findByNames TEST")
    @Test
    void findByNamesTest() {
        // Given
        Member m1 = new Member("member1", 10);
        Member m2 = new Member("member2", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // When
        List<Member> byNames = memberRepository.findByNames(Arrays.asList("member1", "member2"));

        // Then
        for (Member byName : byNames) {
            System.out.println(byName);
        }
    }

}