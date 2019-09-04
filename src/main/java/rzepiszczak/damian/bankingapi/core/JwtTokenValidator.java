package rzepiszczak.damian.bankingapi.core;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenValidator {

    public static boolean validate(String jwt) {

        try {

            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(jwt);

            return true;

        } catch (SignatureException e) {
            return false;
        }
    }
}
