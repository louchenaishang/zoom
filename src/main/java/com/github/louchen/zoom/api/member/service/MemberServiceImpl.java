package com.github.louchen.zoom.api.member.service;

import com.github.louchen.zoom.api.member.model.Member;
import com.github.louchen.zoom.api.member.repository.MemberRepository;
import com.github.louchen.zoom.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Page<Member> findByNameLike(String name, Pageable pageable) {
        return memberRepository.findByNameLike(name, pageable);
    }

}
