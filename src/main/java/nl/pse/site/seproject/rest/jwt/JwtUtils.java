package nl.pse.site.seproject.rest.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtUtils {

    private static final String PASSPHRASE = "Dit moet dan maar een hele lange zin zijn want anders kan ik het geen beveiliging op toe passen. Zal dit lang genoeg zijn?";

    public static Key getKey() {
        byte hmacKey[] = PASSPHRASE.getBytes(StandardCharsets.UTF_8);
        Key key = new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
        return key;
    }
}
