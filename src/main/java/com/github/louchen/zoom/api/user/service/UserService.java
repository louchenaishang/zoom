package com.github.louchen.zoom.api.user.service;

import com.github.louchen.zoom.api.user.model.User;
import com.github.louchen.zoom.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends BaseService<User, Long> {

    User findByUsername(final String username);

    Page<User> findByUsernameLike(String name, Pageable pageable);

    User register(User userToAdd);

    User modify(User userToUpdate);

}
