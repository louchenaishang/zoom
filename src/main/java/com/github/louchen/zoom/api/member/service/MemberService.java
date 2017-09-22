package com.github.louchen.zoom.api.member.service;

import com.github.louchen.zoom.api.member.model.Member;
import com.github.louchen.zoom.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

/**
 * Created by louchen on 2017/9/21.
 */
public interface MemberService extends BaseService<Member, Long> {

    Page<Member> findByNameLike(final @Param("name") String name, final Pageable pageable);

}
