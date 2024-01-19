package com.ddangme.datajpa.controller;

import com.ddangme.datajpa.entity.Member;
import com.ddangme.datajpa.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("userA"));
    }
}
