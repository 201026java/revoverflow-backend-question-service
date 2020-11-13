package com.revature.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.revature.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil implements Serializable {
	
	/**@Author Ryan Clayton
	 * 
	 */
	private static final long serialVersionUID = 5855530699107877768L;
	
	@Value("${environment.rss.secret}")
	private String secret;
	
	
	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, user.getEmail());
	}
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	 //Extract username from token
    public String extractEmail(String token) {
    	return extractClaim(token, Claims::getSubject);
    }

    //Extract token date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Extract from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    //Extract token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //Validate expiration
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    public Boolean validateToken(String token, User user) {
        final String email = extractEmail(token);
        return (email.equals(user.getEmail()) && !isTokenExpired(token));
    }
}
