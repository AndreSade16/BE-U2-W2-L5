package andreasaderi.L5.security;

import andreasaderi.L5.entities.Employee;
import andreasaderi.L5.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JWTTools {
    private final String jwtSecret;

    public JWTTools(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String generateToken(Employee employee) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .subject(String.valueOf(employee.getEmployeeId()))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {

        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parse(token);
        } catch (Exception exception) {
            throw new UnauthorizedException("Error in token verification");
        }
        ;

    }

    public UUID getIdFromToken(String token) {
        return UUID.fromString(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseSignedClaims(token).getPayload().getSubject());
    }

}
