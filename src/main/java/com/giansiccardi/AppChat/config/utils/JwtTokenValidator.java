package com.giansiccardi.AppChat.config.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String jwt=request.getHeader(JwtConst.JWT_HEADER);

      if(jwt!=null){
          jwt=jwt.substring(7);
          try {
              SecretKey key= Keys.hmacShaKeyFor(JwtConst.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
              Claims claims= Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
              String email=String.valueOf(claims.get("email"));

              SecurityContextHolder.getContext();

          }catch (Exception e){
              e.printStackTrace();
              throw new RuntimeException("TOKEN INVALIDO");

          }
      }
        filterChain.doFilter(request,response);
    }
}
