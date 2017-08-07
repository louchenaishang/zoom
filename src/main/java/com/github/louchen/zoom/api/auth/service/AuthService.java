package com.github.louchen.zoom.api.auth.service;

import com.github.louchen.zoom.api.user.model.User;

/**
 * Service - 认证
 *
 * @author louchen
 */
public interface AuthService {
    User register(User userToAdd);

    String login(String username, String password);

    String refresh(String oldToken);
}
