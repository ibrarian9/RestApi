package com.example.restapi.Controllers;

import com.example.restapi.Request.LoginReq;
import com.example.restapi.Response.ErrorRes;
import com.example.restapi.Response.JwtRes;
import com.example.restapi.Service.JwtService;
import com.example.restapi.Service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    JwtService jwtService;

//    Fitur Login
    @PostMapping(value = "/login")
    public ResponseEntity<?> Login(@RequestBody LoginReq loginReq) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwt = jwtService.generateJwtToken(auth);
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

            return ResponseEntity.ok(new JwtRes(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail()
            ));
        } catch (Exception e) {
            ErrorRes res = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

    }

    @PostMapping(value = "/loginn")
    public String login(@RequestBody LoginReq loginReq){
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
        );
        return "token";
    }
}
