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

        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzg5MDY4MTMsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoiZWRkaWUifQ._6_arTN2l2fCUN2L11IZq7uLHCJGqlMKU0J_FMzOOh8");

        System.out.println(verify.getClaim("userId").asInt());
        System.out.println(verify.getClaim("username").asString());
        System.out.println("Expired time: " + verify.getExpiresAt());

    }
}
