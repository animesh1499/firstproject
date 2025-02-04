package org.example.firstproject.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.firstproject.services.impl.JWTServicesImpl;
import org.example.firstproject.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTServicesImpl jwtServices;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklisted:";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Started the filter!");
        //Bearer {token}
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            log.info("Inside extracting token filter!");
            token = authHeader.substring(7);
            username = jwtServices.extractUsername(token);
        }
        if(username != null){
            log.info("Inside invalid token filter!");
            if (Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + token))) {
                log.info("inside redis has key " + request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is invalid (logged out)");
                return;
            }
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(username);

            if(jwtServices.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
