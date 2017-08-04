package com.github.louchen.zoom.api.user.repository;


import com.github.louchen.zoom.api.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by louchen on 2017/6/30.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    User findByUsername(final String username);

    @Query("select u from User u where u.username like CONCAT('%',:username,'%')")
    Page<User> findByUsernameLike(final @Param("username") String username, final Pageable pageable);

}
