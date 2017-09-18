package com.github.louchen.zoom.controller.admin;

import com.github.louchen.zoom.api.channel.model.Channel;
import com.github.louchen.zoom.api.channel.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器 - 渠道
 *
 * @author louchen
 */
@RestController
@RequestMapping(path = "/api/admin/channel")
public class ChannelController {

    @Autowired
    private ChannelRepository channelRepository;

    @GetMapping(path = "")
    public Iterable<Channel> getChannelList() {
        return channelRepository.findAll();
    }

}
