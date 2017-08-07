package com.github.louchen.zoom.api.role.service;

import com.github.louchen.zoom.api.role.exception.InitRoleErrorException;
import com.github.louchen.zoom.api.role.model.Role;
import com.github.louchen.zoom.base.BaseService;

public interface RoleService extends BaseService<Role, Long> {

    Role findDefault() throws InitRoleErrorException;

}
