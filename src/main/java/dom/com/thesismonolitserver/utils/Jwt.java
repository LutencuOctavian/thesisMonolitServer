package dom.com.thesismonolitserver.utils;
import dom.com.thesismonolitserver.security.UserSecurity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class Jwt {
    private String SECRETE_KEY="iojsonwebtokenSignatureAlgorithmassertValidSigningKeytokenSignatureAlgorithmas";

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims= new HashMap<>();
        List<Object> test=new ArrayList<>();
        userDetails.getAuthorities().forEach(authority->{
            claims.put(authority.getAuthority(), authority);
            test.add(authority.getAuthority());
        });
        return createToken(test, userDetails.getUsername(), ((UserSecurity)userDetails).getUserData().getId());
    }
    //    Map<String, GrantedAuthority> claims,  List<Object> list
    private String createToken(List<Object> list, String subject, Long userId) {
        return Jwts.builder()
//                .setClaims(claims)
                .claim("roles", list)
                .claim("userId", userId)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 10000*60*60))
                .signWith(SignatureAlgorithm.HS256, SECRETE_KEY)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRETE_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName= extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
