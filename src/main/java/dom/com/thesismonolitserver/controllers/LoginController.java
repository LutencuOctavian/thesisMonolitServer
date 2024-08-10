package dom.com.thesismonolitserver.controllers;

import dom.com.thesismonolitserver.utils.AuthenticationRequest;
import dom.com.thesismonolitserver.utils.AuthenticationResponse;
import dom.com.thesismonolitserver.utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin
@RequestMapping(path="/api")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userService;
    private final Jwt jwt;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
                           UserDetailsService userService,
                           Jwt jwt) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwt = jwt;
    }

    @PostMapping(path="/login")
    public ResponseEntity<Object> authentificationUser(@RequestBody AuthenticationRequest authenticationRequest){

        String userName = authenticationRequest.getUserName();
        String password =  authenticationRequest.getPassword();
        AuthenticationRequest authRequest=new AuthenticationRequest(userName, password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        final UserDetails userDetails=userService.loadUserByUsername(authRequest.getUserName());
        final String jwtToken=jwt.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
