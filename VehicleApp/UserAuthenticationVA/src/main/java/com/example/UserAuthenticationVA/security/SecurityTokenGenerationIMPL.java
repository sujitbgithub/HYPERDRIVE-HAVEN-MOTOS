package com.example.UserAuthenticationVA.security;

import com.example.UserAuthenticationVA.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SecurityTokenGenerationIMPL implements SecurityTokenGeneration{
    @Override
    public Map<String, String> generateToken(User user) {
        Map<String,String> tokenMapping= new HashMap<>();
        Map<String, Object> userDetails= new HashMap<>();
        userDetails.put("emailId", user.getEmailId());

        long expirationInMilliS= 7 * 24 * 60 * 60 * 1000;//7 days in milliseconds

        Date expirationDate=new Date(System.currentTimeMillis() + expirationInMilliS);
        //String myTokenWithExpiration = Jwts.builder().setClaims(userData).setIssuedAt(new Date( System.currentTimeMillis())).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512,"Secret_Key").compact();



        String myTokenWithExpiration= Jwts.builder().setClaims(userDetails)
                .setSubject(user.getEmailId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, "SecretKey")
                .compact();
        System.out.println(myTokenWithExpiration);

        tokenMapping.put("Token", myTokenWithExpiration);
        tokenMapping.put("Message", "User Logged In Successfully");
        tokenMapping.put("emailId", user.getEmailId());

        return tokenMapping;
    }
}
