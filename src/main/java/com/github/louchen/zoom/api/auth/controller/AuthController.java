package com.github.louchen.zoom.api.auth.controller;

import com.github.louchen.zoom.api.auth.service.AuthService;
import com.github.louchen.zoom.api.user.model.User;
import com.github.louchen.zoom.secruity.JwtAuthenticationRequest;
import com.github.louchen.zoom.secruity.JwtAuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 控制器 - 认证
 *
 * @author louchen
 */
@RestController
@RequestMapping(path = "/api")
@Slf4j
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    /**
     * 注册接口
     * @param addedUser
     * @return
     * @throws AuthenticationException
     */
    @PostMapping(value = "${jwt.route.authentication.register}")
    public User register(@RequestBody User addedUser) throws AuthenticationException {
        return authService.register(addedUser);
    }

    /**
     * 登陆接口
     * @param authenticationRequest
     * @return
     * @throws AuthenticationException
     */
    @PostMapping(value = "${jwt.route.authentication.path}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    /**
     * 刷新token
     * @param request
     * @return
     * @throws AuthenticationException
     */
    @GetMapping(value = "${jwt.route.authentication.refresh}")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    /**
     * 获取登陆信息
     * @param request
     * @return
     */
    @GetMapping(value = "${jwt.route.authentication.principal}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPrincipal(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        return ResponseEntity.ok(principal);
    }

}
