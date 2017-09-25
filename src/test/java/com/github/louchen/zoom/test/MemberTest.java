package com.github.louchen.zoom.test;

import com.github.louchen.zoom.api.member.model.Member;
import com.github.louchen.zoom.api.member.repository.MemberRepository;
import com.test.base.BaseTest;
import com.test.base.RandomMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by louchen on 2017/7/5.
 */
@Slf4j
public class MemberTest extends BaseTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void init() {
        for (int i = 0; i < 1000; i++) {
            Map<String, String> data = RandomMock.getData();
            Member member = new Member();
            member.setName(data.get("name"));
            member.setAmount(BigDecimal.ZERO);
            memberRepository.save(member);
        }
    }

}
