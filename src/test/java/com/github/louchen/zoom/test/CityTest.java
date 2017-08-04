package com.github.louchen.zoom.test;

import com.github.louchen.zoom.api.city.model.City;
import com.github.louchen.zoom.api.city.repository.CityRepository;
import com.test.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by louchen on 2017/7/5.
 */
public class CityTest extends BaseTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void findAll(){
        List<City> all = cityRepository.findAll();
        assertEquals(true, all!=null&&all.size()>0);
    }

}
