package com.example.restapi.Service;

import com.example.restapi.Models.userlogin;
import com.example.restapi.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userlogin userLogin = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found : " + username));
        return UserDetailsImpl.build(userLogin);
    }
}
