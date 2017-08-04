package com.github.louchen.zoom.api.role.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * Created by louchen on 2017/7/27.
 */
@Slf4j
public class RoleRepositoryImpl implements RoleRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

}
