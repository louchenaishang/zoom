package com.github.louchen.zoom.secruity;

import com.github.louchen.zoom.api.user.model.User;
import com.github.louchen.zoom.api.user.model.UserRole;
import com.github.louchen.zoom.api.user.repository.UserRoleRepository;
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

    private UserRoleRepository userRoleRepository;

    public JwtUserFactory(@Autowired UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public JwtUser create(User user) {
        List<UserRole> userRoles = userRoleRepository.findByUser(user);
        List<String> roles = userRoles.stream().map(userRole -> userRole.getRole().getName()).collect(Collectors.toList());

        JwtUser jwt = new JwtUser(
                user.getId().toString(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(roles),
                user.getLastPasswordResetDate()
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

