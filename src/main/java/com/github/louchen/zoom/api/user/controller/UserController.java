package com.github.louchen.zoom.api.user.controller;

import com.github.louchen.zoom.api.user.model.User;
import com.github.louchen.zoom.api.user.repository.UserRepository;
import com.github.louchen.zoom.api.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在 @PreAuthorize 中我们可以利用内建的 SPEL 表达式：比如 'hasRole()' 来决定哪些用户有权访问。
 * 需注意的一点是 hasRole 表达式认为每个角色名字前都有一个前缀 'ROLE_'。所以这里的 'ADMIN' 其实在
 * 数据库中存储的是 'ROLE_ADMIN' 。这个 @PreAuthorize 可以修饰Controller也可修饰Controller中的方法。
 **/
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     *
     * @param name
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<User> getUsers(String name, Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<User> users = StringUtils.isNotBlank(name) ? repository.findByUsernameLike(name, pageRequest) : repository.findAll(pageRequest);
        return users;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    User addUser(@RequestBody User addedUser) {
        return userService.register(addedUser);
    }

    @PostAuthorize("returnObject.username == principal.username or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return repository.findOne(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User one = repository.getOne(id);
        one.setUsername(updatedUser.getUsername());
        one.setSex(updatedUser.getSex());
        one.setRoad(updatedUser.getRoad());
        return repository.save(one);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    User removeUser(@PathVariable Long id) {
        User deletedUser = repository.findOne(id);
        repository.delete(id);
        return deletedUser;
    }

}
