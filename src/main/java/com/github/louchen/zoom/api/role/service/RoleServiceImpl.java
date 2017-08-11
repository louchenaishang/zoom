package com.github.louchen.zoom.api.role.service;

import com.github.louchen.zoom.api.role.exception.InitRoleErrorException;
import com.github.louchen.zoom.api.role.model.Role;
import com.github.louchen.zoom.api.role.repository.RoleRepository;
import com.github.louchen.zoom.base.BaseServiceImpl;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService, InitializingBean {

    private Role DEFAULT_ROLE;
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final Set<String> ROLES = Sets.newHashSet(ROLE_USER, ROLE_ADMIN);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        ROLES.forEach(name -> {
            Role role = roleRepository.findByName(name);
            if (role == null) {
                role = new Role();
                role.setName(name);
                roleRepository.save(role);
            }
        });

        DEFAULT_ROLE = roleRepository.findByName(ROLE_USER);
        if (DEFAULT_ROLE == null) {
            throw new InitRoleErrorException();
        }
    }


    @Override
    public Role findDefault() throws InitRoleErrorException {
        return DEFAULT_ROLE;
    }

}
