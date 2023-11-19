package com.example.restapi;

import com.example.restapi.Models.Role;
import com.example.restapi.Models.User;
import com.example.restapi.Service.UserDetailsImpl;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.event.AuthenticationFailureProxyUntrustedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthenticationException;
import java.security.DrbgParameters;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private final String secret_key = "secret";
    private final JwtParser jwtParser;

    public JwtUtil() {
       this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public String createToken(User user) {
        Date tokenCreateTime = new Date();
        long accessTokenValid = 60 * 60 * 1000;
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValid));
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer(user.getEmail())
                .claim("roles", user.getRoles().toString())
                .setIssuedAt(new Date())
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS512, secret_key)
                .compact();
    }

    public Claims parseJwtClaims(String token){
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req){
        try {
            String token = resolveToken(req);
            if (token != null){
                return parseJwtClaims(token);
            }
            return null;
        } catch (Exception e){
            req.setAttribute("expired", e.getMessage());
            throw e;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String TOKEN_HEADER = "Authorization";
        String TOKEN_PREFIX = "Bearer ";
        String bearerToken = req.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validatedClaims(Claims claims) throws AuthenticationException {
        return claims.getExpiration().after(new Date());
    }


    public String getEmail(Claims claims){
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims){
        return (List<String>) claims.get("roles");
    }

}
