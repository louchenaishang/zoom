package com.github.louchen.zoom.api.auth;

import com.github.louchen.zoom.api.role.repository.RoleRepository;
import com.github.louchen.zoom.api.user.repository.UserRepository;
import com.github.louchen.zoom.api.role.model.Role;
import com.github.louchen.zoom.api.user.model.User;
import com.github.louchen.zoom.api.user.model.UserRole;
import com.github.louchen.zoom.api.user.repository.UserRoleRepository;
import com.github.louchen.zoom.secruity.JwtTokenUtil;
import com.github.louchen.zoom.secruity.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService,InitializingBean {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private Role DEFAULT_ROLE;
    private static final String DEFAULT_ROLE_NAME = "ROLE_USER";

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserRoleRepository userRoleRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE_NAME);
        if(defaultRole==null){
            defaultRole = new Role();
            defaultRole.setName(DEFAULT_ROLE_NAME);
            roleRepository.save(defaultRole);
        }
        this.DEFAULT_ROLE = defaultRole;
    }

    @Override
    @Transactional
    public User register(User userToAdd) {
        final String username = userToAdd.getUsername();
        if(userRepository.findByUsername(username)!=null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
        userRepository.save(userToAdd);

        UserRole userRole = new UserRole();
        userRole.setUser(userToAdd);
        userRole.setRole(DEFAULT_ROLE);
        userRoleRepository.save(userRole);

        return userToAdd;
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

}
