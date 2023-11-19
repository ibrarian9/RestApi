package com.example.restapi.Service;

import com.example.restapi.Models.User;
import com.example.restapi.Repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetail implements UserDetailsService {

    private final UserRepository userRepo;

    public UserDetail(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if ( user == null){
            throw new UsernameNotFoundException("username not found");
        }
        return new UserDetailsImpl(user);
    }
}
