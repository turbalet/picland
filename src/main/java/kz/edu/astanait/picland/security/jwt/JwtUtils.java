package kz.edu.astanait.picland.security.jwt;

import io.jsonwebtoken.*;
import kz.edu.astanait.picland.model.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

import static java.util.Date.from;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    @Value("${picland.jwtSecret}")
    private String jwtSecret;

    @Value("${picland.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateTokenWithUsername(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws MalformedJwtException {
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e){
            logger.error("Invalid jwt signature: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Jwt token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("Jwt token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            logger.error("Jwt claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
