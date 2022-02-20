package com.MakeMyTrip;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class jwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = null;

        String username = null;
        String jwt = null;
        if(request.getCookies()!=null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) header = cookie.getValue();
            }
            if (header != null && header.startsWith("Bearer")) {
                jwt = header.substring(6);
                try {
                    username = jwtUtil.getUsernameFromToken(jwt);
                    UserDetails details = customerUserDetailsService.loadUserByUsername(username);
//                System.out.println(details);
//                response.addHeader("jwt", jwtUtil.generateToken(details));
                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token");
                } catch (ExpiredJwtException e) {
                    System.out.println("JWT Token has expired");
                }
            }
        }

        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.customerUserDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateAndGetUsernameFromToken(jwt,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
