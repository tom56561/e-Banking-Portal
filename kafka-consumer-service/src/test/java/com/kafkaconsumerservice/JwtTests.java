package com.kafkaconsumerservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Calendar;


public class JwtTests {

    @Test
    void contextLoads(){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 100);
        String token = JWT.create()
                .withClaim("userId", 21)
                .withClaim("username", "eddie")
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256("!QS*#DA"));
        System.out.println(token);
    }

    @Test
    public void testJwt(){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!QS*#DA")).build();

        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzg3MzEyMzQsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoiZWRkaWUifQ.adCiVo9AePdh07PIpfGRj6BmbiIckBlHkRDsbxqu3OM");

        System.out.println(verify.getClaim("userId").asInt());
        System.out.println(verify.getClaim("username").asString());
        System.out.println("Expired time: " + verify.getExpiresAt());

    }
}
