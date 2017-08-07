package com.github.louchen.zoom.secruity;

import com.github.louchen.zoom.api.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public JwtUser create(User user) {
        List<String> roles = user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());

        JwtUser jwt = new JwtUser(
                user.getId().toString(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(roles),
                user.getUpdateTime()
        );

        log.info("create jwt,{}", jwt.toString());

        return jwt;
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

