package com.github.louchen.zoom.api.channel.model;

import com.github.louchen.zoom.api.city.model.City;
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

    private City city;

    @TimeToLive
    private Long expiration=100l;

}
