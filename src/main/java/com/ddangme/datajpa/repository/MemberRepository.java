package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.domain.Member;
import com.ddangme.datajpa.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    // 정적 쿼리에만 사용하는 것이 좋다. 동적 쿼리가 필요할 경우 QueryDLS를 사용한다.
    @Query("SELECT m FROM Member m WHERE m.username = :username AND m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("SELECT m.username FROM Member m")
    List<String> findUsernameList();

    @Query("SELECT new com.ddangme.datajpa.dto.MemberDto(m.id, m.username, t.name) FROM Member m JOIN m.team t")
    List<MemberDto> findMemberDto();

    @Query("SELECT m FROM Member m WHERE m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username);   // 컬렉션 -- 검색 결과가 없을 경우 null이 아닌 빈 컬렉션으로 반환된다.

    Member findOneByUsername(String username);          // 단건 -- 조회 결과가 여러 개일 경우 Error가 발생한다.

    Optional<Member> findOptionalByUsername(String username); // 단건 Optional --  조회 결과가 여러 개일 경우 Error가 발생한다.

}
