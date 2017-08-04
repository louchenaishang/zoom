package com.github.louchen.zoom.api.city.controller;

import com.github.louchen.zoom.api.city.repository.CityRepository;
import com.github.louchen.zoom.api.city.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by louchen on 2017/6/30.
 */
@RestController
@RequestMapping(path = "/api/city")
@PreAuthorize("hasRole('USER')")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping(path = "")
    public List<City> getCityList() {
        return cityRepository.findAll();
    }

    @PostMapping(path = "")
    public City newOne(@RequestBody City city) {
        City newOne = cityRepository.save(city);
        return cityRepository.save(newOne);
    }

    @DeleteMapping(path = "/{id}")
    public Boolean deleteOne(@PathVariable Long id) {
        cityRepository.delete(id);
        return true;
    }

    @GetMapping(path = "count")
    public Long count(@RequestParam String name) {
        return 0l;
    }

}
