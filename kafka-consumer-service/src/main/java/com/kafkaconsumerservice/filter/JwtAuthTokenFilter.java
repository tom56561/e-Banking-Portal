package com.kafkaconsumerservice.filter;


import com.auth0.jwt.interfaces.Claim;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;


import java.io.IOException;


@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //take token
        String token = request.getHeader("token");
        if(token == null || token.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }

//        try {
//            Jws<Claims> jws = Jwts.parserBuilder()
//                    .setSigningKey(secretKey.getBytes())
//                    .build()
//                    .parseClaimsJws(jwtString);
//
//            Claims claims = jws.getBody();
//            String username = claims.getSubject();
//            System.out.println("Username: " + username);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //Parsing token
//        Claim claims = JwtUnit.parseJWT(token);
//        String userId = claims.getClass();




    }
}
