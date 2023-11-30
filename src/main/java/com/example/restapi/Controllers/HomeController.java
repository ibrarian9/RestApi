package com.example.restapi.Controllers;

import com.example.restapi.Request.LoginReq;
import com.example.restapi.Response.JwtRes;
import com.example.restapi.Service.JwtService;
import com.example.restapi.Service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    JwtService jwtService;

//    Fitur Login
    @PostMapping(value = "/login")
    public ResponseEntity<?> authLogin(@ModelAttribute LoginReq loginReq) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtService.generateJwtToken(auth);

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        List<String> role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new JwtRes(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role
        ));
    }

    @PostMapping(value = "/loginn")
    public String login(@ModelAttribute LoginReq loginReq){
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
        );
        return "berhasil login";
    }
}
