package com.github.louchen.zoom.api.user.service;

import com.github.louchen.zoom.api.user.model.User;

public interface UserService {

    User findByUsername(final String username);

    User register(User userToAdd);

}
