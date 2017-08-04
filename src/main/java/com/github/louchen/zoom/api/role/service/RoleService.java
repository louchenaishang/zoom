package com.github.louchen.zoom.api.role.service;

import com.github.louchen.zoom.api.role.exception.InitRoleErrorException;
import com.github.louchen.zoom.api.role.model.Role;

public interface RoleService {

    Role findDefault() throws InitRoleErrorException;

}
