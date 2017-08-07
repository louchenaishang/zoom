package com.github.louchen.zoom.api.user.listener;

import com.github.louchen.zoom.api.role.service.RoleService;
import com.github.louchen.zoom.api.user.event.UserRegisteredEvent;
import com.github.louchen.zoom.api.user.model.User;
import com.github.louchen.zoom.api.user.service.UserService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by louchen on 2017/7/10.
 */
@Component
@Slf4j
public class UserEventListener {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @EventListener
    public void handle(UserRegisteredEvent userRegisteredEvent) {
        log.info("userRegisteredEvent,{}", userRegisteredEvent);
        User userToAdd = userRegisteredEvent.getUser();
        userToAdd.setRoles(Sets.newHashSet(roleService.findDefault()));
        userService.update(userToAdd);
    }

}
