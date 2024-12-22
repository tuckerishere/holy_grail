package com.secretcow.holygrail.services;

import com.secretcow.holygrail.entities.UserDetail;
import com.secretcow.holygrail.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail userDetail = userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("Username not found"));

        return User.withUsername(userDetail.getUsername())
                .password(userDetail.getPassword())
                .disabled(!userDetail.getActive())
                .build();
    }
}
