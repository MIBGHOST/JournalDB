package com.springboot.journalapp.journalapp.service;

import com.springboot.journalapp.journalapp.entity.UserEntity;
import com.springboot.journalapp.journalapp.repository.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntityRepo userEntityRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity byUsername = userEntityRepo.getByUsername(username);
        if(byUsername!=null){
            return User.builder()
                    .username(byUsername.getUsername())
                    .password(byUsername.getPassword())
                    .roles(byUsername.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("user not found: " + username);
    }
}
