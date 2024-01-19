package com.ddangme.datajpa.repository;

import com.ddangme.datajpa.domain.Member;
import com.ddangme.datajpa.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

//    @Query(name = "Member.findByUsername")
//    List<Member> findByUsername(@Param("username") String username);

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

    // @Query 어노테이션을 사용하지 않아도 동작하지만, 성능이 저하됐을 때 사용하면 좋다.
    @Query(value = "SELECT m FROM Member m LEFT JOIN m.team t",
            countQuery = "SELECT count(m) FROM Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // 변경되는 코드임을 알려주어야 한다.
    // clearAutomatically = true를 작성해 주어야 영속성 컨텍스트를 초기화해준다.
    // 같은 트랙잭션 안에서 조회할 일이 있다면 꼭 해당 구문을 작성해 주어야 한다.
    // clearAutomatically = true 혹은 em.clear를 사용해 주어야 한다.
    @Query("UPDATE Member m SET m.age = m.age + 1 WHERE m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // FETCH : 연관된 TEAM도 함께 조회한다.
    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.team")
    List<Member> findMemberFetchJoin();

    // 공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // JPQL + 엔티티 그래프
//    @EntityGraph(attributePaths = {"team"})
//    @Query("SELECT m FROM Member m")
    @EntityGraph("Member.all")
    @Query("SELECT m FROM Member m")
    List<Member> findMemberEntityGraph();

    // 메서드 이름으로 쿼리에서 특히 편리하다.
    @EntityGraph("Member.all")
    List<Member> findByUsername(String username);

}
