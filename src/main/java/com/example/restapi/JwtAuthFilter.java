package com.example.restapi;

import com.example.restapi.Models.Role;
import com.example.restapi.Models.User;
import com.example.restapi.Repositories.UserRepository;
import com.example.restapi.Service.UserDetail;
import com.example.restapi.Service.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;
    UserDetail userDetail;
    UserRepository userRepo;

    public JwtAuthFilter(JwtUtil jwtUtil, ObjectMapper mapper) {
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> errDetail = new HashMap<>();

        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            //  Set UserDetails on Spring security context
            Claims claims = jwtUtil.resolveClaims(request);
            if (jwtUtil.validatedClaims(claims)){
                String user = claims.getSubject();
                String roles = (String) claims.get("roles");
                UserDetails userDetails = userDetail.loadUserByUsername(user);
                User user1 = new User();
                roles = roles.replace("[", "").replace("]", "");
                String[] roleName = roles.split(",");

                for (String a : roleName) {
                    user1.addRole(new Role(a));
                }

                user1.setUsername(user);
                System.out.println(roles);
                System.out.println(user);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e){
            errDetail.put("message", "Error Brohh!");
            errDetail.put("details", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getWriter(), errDetail);
        }
        filterChain.doFilter(request, response);
    }
}
