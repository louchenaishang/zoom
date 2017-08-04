package com.github.louchen.zoom.api.user.service;

import com.github.louchen.zoom.api.user.event.UserRegisteredEvent;
import com.github.louchen.zoom.api.user.model.User;
import com.github.louchen.zoom.api.user.repository.UserRepository;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User register(User userToAdd) {
        final String username = userToAdd.getUsername();
        if (findByUsername(username) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        final String rawPassword = userToAdd.getPassword();
        final String rawPassword = userToAdd.getPassword() == null ? "123456" : userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
        userRepository.save(userToAdd);

        applicationEventPublisher.publishEvent(new UserRegisteredEvent(this, userToAdd));

        return userToAdd;
    }

}
