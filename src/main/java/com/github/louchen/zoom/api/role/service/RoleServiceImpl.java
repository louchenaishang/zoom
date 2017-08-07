package com.github.louchen.zoom.api.role.service;

import com.github.louchen.zoom.api.role.exception.InitRoleErrorException;
import com.github.louchen.zoom.api.role.model.Role;
import com.github.louchen.zoom.api.role.repository.RoleRepository;
import com.github.louchen.zoom.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService, InitializingBean {

    private Role DEFAULT_ROLE;
    private static final String DEFAULT_ROLE_NAME = "ROLE_USER";

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE_NAME);
        if (defaultRole == null) {
            defaultRole = new Role();
            defaultRole.setName(DEFAULT_ROLE_NAME);
            roleRepository.save(defaultRole);
        }
        this.DEFAULT_ROLE = defaultRole;
        if (DEFAULT_ROLE == null) {
            throw new InitRoleErrorException("初始化失败");
        }
    }


    @Override
    public Role findDefault() throws InitRoleErrorException {
        return DEFAULT_ROLE;
    }

}
