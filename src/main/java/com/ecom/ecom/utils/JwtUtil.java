package com.ecom.ecom.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
    public class JwtUtil {

    private static final String SECRET = "27189231827BG789088789234KN5K34H5JOKB4B34HJ524565644564LK3N34534KB545KB64K56BB56";

    public String generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);

    }

    private String createToken(Map<String, Object> claims, String userName) {
       return Jwts.builder()
               .setClaims(claims)
               .setSubject(userName)
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 30)) // 10 minutes
               .signWith(getSignkey(), SignatureAlgorithm.HS256).compact();


    }

    private SecretKey getSignkey() {
        byte[] keybytes = Decoders.BASE64.decode(SECRET);
//        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Keys.hmacShaKeyFor(keybytes);
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
   public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
   }

    private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSignkey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
