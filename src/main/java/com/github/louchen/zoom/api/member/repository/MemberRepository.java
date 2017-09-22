package com.github.louchen.zoom.api.member.repository;


import com.github.louchen.zoom.api.member.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by louchen on 2017/9/21.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.name like CONCAT('%',:name,'%')")
    Page<Member> findByNameLike(final @Param("name") String name, final Pageable pageable);

}
