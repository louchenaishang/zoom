package com.github.louchen.zoom.test;

import com.github.louchen.zoom.api.channel.model.Channel;
import com.github.louchen.zoom.api.channel.repository.ChannelRepository;
import com.test.base.BaseTest;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by louchen on 2017/7/17.
 */
public class ChannelTest extends BaseTest {

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    public void create(){
        for(int i=0;i<10;i++){
            Channel channel = new Channel();
            channel.setName(RandomStringUtils.random(10));
            channelRepository.save(channel);
        }
    }

}
