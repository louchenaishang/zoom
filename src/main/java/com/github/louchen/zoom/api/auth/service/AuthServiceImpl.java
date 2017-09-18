package com.github.louchen.zoom.api.auth.service;

import com.github.louchen.zoom.api.user.model.User;
import com.github.louchen.zoom.api.user.service.UserService;
import com.github.louchen.zoom.secruity.JwtTokenStore;
import com.github.louchen.zoom.secruity.JwtTokenUtil;
import com.github.louchen.zoom.secruity.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 认证
 *
 * @author louchen
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private JwtTokenStore jwtTokenStore;
    private UserService userService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            JwtTokenStore jwtTokenStore,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtTokenStore = jwtTokenStore;
        this.userService = userService;
    }

    @Override
    @Transactional
    public User register(User userToAdd) {
        return userService.register(userToAdd);
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails);

        // save token
        jwtTokenStore.set(jwtTokenUtil.getIdFromToken(token));

        return token;

//        // Reload password post-security so we can generate token
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        final String token = jwtTokenUtil.generateToken(userDetails);
//        return token;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

}
