package com.bharath.learning.socialmediablog_app_rta_33.service.impl;

import com.bharath.learning.socialmediablog_app_rta_33.model.RoleEntity;
import com.bharath.learning.socialmediablog_app_rta_33.model.UserEntity;
import com.bharath.learning.socialmediablog_app_rta_33.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SocialMediaBlogCustomizedUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

     // Load user by username or email from DB
     UserEntity userEntity = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with Email or Username::" + usernameOrEmail));

     //Fetch all the Roles of User
        Set<RoleEntity> userRoles =  userEntity.getRoles();

      //Convert or Map User Roles into Granted Authority
     Set<GrantedAuthority> grantedAuthoritySet =  userRoles.stream()
             .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        //Create Spring Security User
        User user = new User(userEntity.getEmail(), userEntity.getPassword(), grantedAuthoritySet);

        return user;
    }
}
