package com.github.louchen.zoom.test;

import com.github.louchen.zoom.api.user.repository.UserRepository;
import com.github.louchen.zoom.api.user.model.User;
import com.test.base.BaseTest;
import com.test.base.RandomMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by louchen on 2017/7/5.
 */
@Slf4j
public class UserTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void init() {
        Map<String, String> data = RandomMock.getData();
        User user = new User();
        user.setUsername(data.get("name"));
        user.setMobile(data.get("tel"));
        user.setPassword(data.get("tel"));
        user.setSex(data.get("sex"));
        user.setEmail(data.get("email"));
        userRepository.save(user);

        log.info(data.toString());
    }

}
