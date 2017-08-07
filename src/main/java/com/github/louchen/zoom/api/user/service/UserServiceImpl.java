package com.github.louchen.zoom.api.user.service;

import com.github.louchen.zoom.api.user.event.UserRegisteredEvent;
import com.github.louchen.zoom.api.user.model.User;
import com.github.louchen.zoom.api.user.repository.UserRepository;
import com.github.louchen.zoom.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findByUsernameLike(String name, Pageable pageable) {
        return StringUtils.isNotBlank(name) ? userRepository.findByUsernameLike(name, pageable) : userRepository.findAll(pageable);
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
        userRepository.save(userToAdd);

        applicationEventPublisher.publishEvent(new UserRegisteredEvent(this, userToAdd));

        return userToAdd;
    }

}
