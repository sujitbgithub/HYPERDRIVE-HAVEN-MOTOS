package com.example.VehicleAppForUSerAndAdmin.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilteration extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;


        String authHeader= httpServletRequest.getHeader("Authorization");
        System.out.println("**********JWT AuthHeader****************");
        System.out.println(authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.getWriter().write("Invalid Or Missing Token");
                return;
            }
        String token = authHeader.substring(7);
        System.out.println("*******************NEW PArt TOKEN*******************");
        System.out.println(token);
        Claims claims = Jwts.parser().setSigningKey("SecretKey").parseClaimsJws(token).getBody();
        httpServletRequest.setAttribute("claims", claims);
        System.out.println("claims::-->  "+ claims);

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
