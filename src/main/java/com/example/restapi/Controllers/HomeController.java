package com.example.restapi.Controllers;

import com.example.restapi.DTO.LoginDto;
import com.example.restapi.DTO.RegisterDto;
import com.example.restapi.JwtUtil;
import com.example.restapi.Models.Role;
import com.example.restapi.Models.User;
import com.example.restapi.Repositories.RoleRepository;
import com.example.restapi.Repositories.UserRepository;
import com.example.restapi.Response.ErrorRes;
import com.example.restapi.Response.LoginRes;
import com.example.restapi.Service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class HomeController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder passEncod;
    @Autowired
    private JwtUtil jwtUtil;

//    Fitur Login
    @PostMapping("/login")
    public ResponseEntity<String> authAdmin (@RequestBody LoginDto login){
        Authentication authh = authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authh);
        return new ResponseEntity<>("User Login Success", HttpStatus.OK);
    }

//    Fitur Login ke 2
    @PostMapping("/signIn")
    public ResponseEntity<?> login(@RequestBody LoginDto login){

        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            var user = auth.getName();
            var authh = auth.getAuthorities();
            User user1 = userRepo.findByUsername(user);
            String token = jwtUtil.createToken(user1);
            LoginRes res = new LoginRes(authh.toString(), user, token);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (BadCredentialsException e){
            ErrorRes errorRes = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        } catch (Exception e){
            ErrorRes errorRes = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        }
    }

//    Fitur Register
//    @PostMapping("/register")
//    public ResponseEntity<?> regisAdmin (@RequestBody RegisterDto register){
//        if (userRepo.existsByUsername(register.getUsername())){
//            return new ResponseEntity<>("Username is Already Exist", HttpStatus.BAD_REQUEST);
//        }
//
//        if (userRepo.existsByEmail(register.getEmail())){
//            return new ResponseEntity<>("Email is Already Exist", HttpStatus.BAD_REQUEST);
//        }
//
//        User user = new User();
//        user.setName(register.getName());
//        user.setUsername(register.getUsername());
//        user.setEmail(register.getEmail());
//        user.setPassword(passEncod.encode(register.getPassword()));
//
//        if (roleRepo.findByName("ROLE_ADMIN").isPresent()){
//            Role roles = roleRepo.findByName("ROLE_ADMIN").get();
//            user.setRoles(Collections.singleton(roles));
//            userRepo.save(user);
//        } else {
//            System.out.println("Data Not Found");
//        }
//        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
//    }
}
