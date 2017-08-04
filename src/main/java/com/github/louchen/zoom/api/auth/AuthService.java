package com.github.louchen.zoom.api.auth;

import com.github.louchen.zoom.api.user.model.User;

public interface AuthService {
    User register(User userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}
