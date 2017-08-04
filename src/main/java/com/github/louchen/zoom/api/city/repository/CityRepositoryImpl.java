package com.github.louchen.zoom.api.city.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;


/**
 * Created by louchen on 2017/7/1.
 */
@Slf4j
public class CityRepositoryImpl implements CityRepositoryCustom {

    @Autowired
    private EntityManager entityManager;


}
