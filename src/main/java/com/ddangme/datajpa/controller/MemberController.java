package com.ddangme.datajpa.controller;

import com.ddangme.datajpa.dto.MemberDto;
import com.ddangme.datajpa.entity.Member;
import com.ddangme.datajpa.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    // 도메인 클래스 컨버터 사용 전
    @GetMapping("/membersV1/{id}")
    public String findMemberV1(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 도메인 클래스 컨버터 사용 후
    // 도메인 클래스 컨버터로 엔티티를 파라미터로 받았으면, 이 엔티티는 단순 조회용으로만 사용해야 한다.
    // 트랜잭션이 없는 범위에서 엔티티를 조회했기 때문에, 엔티티를 변경해도 DB에 반영되지 않는다.
    // 강사 : 권장하지 않는 내용
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }

//    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }


    // 테스트 링크 : http://localhost:8080/members?page=0&size=3&sort=id,desc&sort=username,desc
    // 페이지에 대한 값이 넘어오지 않으면 기본 값으로 설정된다. (기본 페이지 사이즈 : 20, 최대 페이지 사이즈 : 2000)
    // yaml에서 spring.data.web.pageable.default-page-size: 20
    // spring.data.web.pageable.max-page-size: 2000
    @GetMapping("members")
    public Page<Member> list(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @GetMapping("members2")
    public Page<Member> list2(@PageableDefault(size = 12, sort = "username",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @GetMapping("members3")
    public Page<MemberDto> list3(Pageable pageable) {
        return memberRepository.findAll(pageable).map(MemberDto::new);
    }
}
