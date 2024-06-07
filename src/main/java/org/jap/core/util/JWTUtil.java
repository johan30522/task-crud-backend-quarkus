package org.jap.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {



    public static String generateToken(String username, String csrfToken) {

        return Jwts.builder()
                .setSubject(username)
                .claim("csrfToken", csrfToken)
                .setExpiration(new Date(System.currentTimeMillis() + UtilConstants.EXPIRATION_TIME_JWT))
                .signWith(SignatureAlgorithm.HS512, UtilConstants.SECRET_KEY_JWT)
                .compact();
    }

    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(UtilConstants.SECRET_KEY_JWT)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUsernameFromToken(String token) {
        return validateToken(token).getSubject();
    }

    public static String getCSRFTokenFromToken(String token) {
        return (String) validateToken(token).get("csrfToken");
    }

    public static boolean isTokenExpired(String token) {
        Date expiration = validateToken(token).getExpiration();
        return expiration.before(new Date());
    }
}
