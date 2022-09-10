package com.sing3demons.springbootapi.config.token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sing3demons.springbootapi.service.TokenService;

public class TokenFilter extends GenericFilterBean {

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest requestServlet, ServletResponse responseServlet, FilterChain arg2)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) requestServlet;
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (ObjectUtils.isEmpty(authorization)) {
            arg2.doFilter(requestServlet, responseServlet);
            return;
        }

        if (!authorization.startsWith("Bearer ")) {
            arg2.doFilter(requestServlet, responseServlet);
            return;
        }

        String token = authorization.substring(7);
        DecodedJWT verify = tokenService.verify(token);
        if (verify == null) {
            arg2.doFilter(requestServlet, responseServlet);

            return;
        }
        String sub = verify.getSubject();
        String role = verify.getClaim("role").asString();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sub,
                "(protected)", authorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);
        arg2.doFilter(requestServlet, responseServlet);
    }

}
