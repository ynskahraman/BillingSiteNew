package com.proje.billing_site.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "your_secure_256_bit_key_here_your_secure_256_bit_key_here";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 saat
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Kullanıcı için JWT token oluşturur.
     *
     * @param email Kullanıcı email adresi
     * @param role  Kullanıcı rolü (ADMIN veya USER)
     * @return Oluşturulan JWT token
     */
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role) // Rol bilgisi buraya ekleniyor
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Kullanıcı için özelleştirilmiş JWT token oluşturur.
     * Token'a ek bilgi (örneğin userId) eklemek için kullanılır.
     *
     * @param email Kullanıcı email adresi
     * @param role  Kullanıcı rolü (ADMIN veya USER)
     * @param extraClaims Ek bilgiler (key-value çiftleri)
     * @return Oluşturulan JWT token
     */
    public String generateTokenWithClaims(String email, String role, Claims extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Token'ı doğrular.
     *
     * @param token Doğrulanacak JWT token
     * @return Token geçerliyse true, değilse false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.err.println("Token doğrulama hatası: " + e.getMessage());
            return false;
        }
    }

    /**
     * Token'dan tüm bilgileri çıkarır.
     *
     * @param token JWT token
     * @return Claims nesnesi
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Token'dan email bilgisini çıkarır.
     *
     * @param token JWT token
     * @return Email bilgisi
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Token'dan rol bilgisini çıkarır.
     *
     * @param token JWT token
     * @return Kullanıcının rolü
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Token'dan özel bir claim'i çıkarır.
     *
     * @param token JWT token
     * @param claimKey Claim anahtarı
     * @param clazz Claim türü
     * @return Claim değeri
     */
    public <T> T extractClaim(String token, String claimKey, Class<T> clazz) {
        return extractAllClaims(token).get(claimKey, clazz);
    }

    /**
     * Token süresinin dolup dolmadığını kontrol eder.
     *
     * @param token JWT token
     * @return Süresi dolmuşsa true, değilse false
     */
    public boolean isTokenExpired(String token) {
        Date expirationDate = extractAllClaims(token).getExpiration();
        return expirationDate.before(new Date());
    }
}



