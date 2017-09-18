package com.github.louchen.zoom.controller;


import com.github.louchen.zoom.utils.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@Controller
@RequestMapping("/")
public class HomeController{

    /**
     * 首页
     */
    @GetMapping("")
    public void index() throws IOException {
        WebUtils.getResponse().sendRedirect("/index.html");
    }



}