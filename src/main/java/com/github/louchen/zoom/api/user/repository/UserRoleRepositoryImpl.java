package com.github.louchen.zoom.api.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

@Slf4j
public class UserRoleRepositoryImpl implements UserRoleRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

}
