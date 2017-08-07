package com.github.louchen.zoom.api.channel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

/**
 * Created by louchen on 2017/7/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Channel")
public class Channel {


    @Id
    private String id;

    private String name;

    @TimeToLive
    private Long expiration=100l;

}
