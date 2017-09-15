package com.github.louchen.zoom.api.user.service;

import com.github.louchen.zoom.api.user.event.UserRegisteredEvent;
import com.github.louchen.zoom.api.user.model.User;
import com.github.louchen.zoom.api.user.repository.UserRepository;
import com.github.louchen.zoom.base.BaseServiceImpl;
import com.github.louchen.zoom.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService, InitializingBean {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

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
        AssertUtils.notNull(userToAdd);
        AssertUtils.isNull(userToAdd.getId());
        AssertUtils.hasText(userToAdd.getUsername());
        AssertUtils.hasText(userToAdd.getPassword());

        final User userInStorage = findByUsername(userToAdd.getUsername());
        AssertUtils.isNull(userInStorage);

        userToAdd.setPassword(passwordEncoder.encode(userToAdd.getPassword()));
        userRepository.save(userToAdd);

        applicationEventPublisher.publishEvent(new UserRegisteredEvent(this, userToAdd));

        return userToAdd;
    }

    @Override
    @Transactional
    public User modify(User userToUpdate) {
        AssertUtils.notNull(userToUpdate);

        User user = find(userToUpdate.getId());
        user.setSex(userToUpdate.getSex());
        user.setEmail(userToUpdate.getEmail());
        user.setMobile(userToUpdate.getMobile());
        userRepository.save(user);

        return userToUpdate;
    }

}
