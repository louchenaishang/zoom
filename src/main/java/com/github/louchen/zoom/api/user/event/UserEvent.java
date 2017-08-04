package com.github.louchen.zoom.api.user.event;

import com.github.louchen.zoom.api.user.model.User;
import org.springframework.context.ApplicationEvent;

/**
 * Created by louchen on 2017/7/10.
 */
public abstract class UserEvent extends ApplicationEvent {

    /**
     * 用户
     */
    private User user;

    /**
     * 构造方法
     *
     * @param source
     *            事件源
     * @param user
     *            用户
     */
    public UserEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    /**
     * 获取用户
     *
     * @return 用户
     */
    public User getUser() {
        return user;
    }

}