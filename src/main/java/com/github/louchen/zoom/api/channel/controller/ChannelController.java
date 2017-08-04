package com.github.louchen.zoom.api.channel.controller;

import com.github.louchen.zoom.api.channel.model.Channel;
import com.github.louchen.zoom.api.channel.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by louchen on 2017/6/30.
 */
@RestController
@RequestMapping(path = "/api/channel")
public class ChannelController {

    @Autowired
    private ChannelRepository channelRepository;

    @GetMapping(path = "")
    public Iterable<Channel> getChannelList() {
        return channelRepository.findAll();
    }

}
