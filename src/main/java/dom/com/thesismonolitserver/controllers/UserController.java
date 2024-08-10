package dom.com.thesismonolitserver.controllers;


import dom.com.thesismonolitserver.dtos.UserDTO;
import dom.com.thesismonolitserver.dtos.UserRegisterDTO;
import dom.com.thesismonolitserver.services.user_service.IUserService;
import dom.com.thesismonolitserver.validators.OnCreate;
import dom.com.thesismonolitserver.validators.OnUpdate;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(@Qualifier("defaultUserService") IUserService userService) {
        this.userService = userService;
    }

    @Validated({OnCreate.class})
    @RequestMapping(path="/new-user", method = RequestMethod.POST)
    public ResponseEntity<Object> createNewUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) throws MessagingException, IOException {
        Long userId = userService.createUser(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    @RequestMapping(path="/email_validation/{id}/{code}", method = RequestMethod.GET)
    public ResponseEntity<String> emailValidation(@PathVariable("id") Long userId, @PathVariable("code") Integer code, HttpServletResponse httpServletResponse) throws URISyntaxException {
            userService.userValidateEmail(userId, code);
        URI projectURI = new URI("http://localhost:5173/succesuful-validate-phone");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(projectURI);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

//            httpServletResponse.setHeader("Location", "http://localhost:5173/succesuful-validate-phone");
//            httpServletResponse.setStatus(302);
//            return ResponseEntity.ok("OK :-)))))   true");
    }

    @RequestMapping(path="/phone_validation/{userId}/{code}", method = RequestMethod.GET)
    public ResponseEntity<String> phoneValidation(@PathVariable("userId") Long userId, @PathVariable("code") Integer code){
            userService.userValidatePhone(userId, code);
            return ResponseEntity.ok("OK :-)))))   true");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path="/all-user", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllUsers() throws MessagingException, IOException {
        List<UserDTO> allUserWithRolesAndPrivilegesData = userService.getAllUserWithRolesAndPrivilegesData();
        return ResponseEntity.status(HttpStatus.OK).body(allUserWithRolesAndPrivilegesData);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Validated({OnUpdate.class})
    @RequestMapping(path="/update-user", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) throws MessagingException, IOException {
        userService.updateUser(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.OK).body("OK user updated!");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path="/delete-user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) throws MessagingException {
            userService.deleteUser(userId);
            return ResponseEntity.ok("USER WAS DELETED   true");
    }

    @RequestMapping(path = "/new-roles-user", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewRolesForUser(@RequestBody @Valid UserDTO userDTO){
        userService.saveNewRolesForUser(userDTO);
        return ResponseEntity.ok("OKKK");
    }

    @RequestMapping(path = "/delete-roles-user", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteRolesForUser(@RequestBody @Valid UserDTO userDTO){
        userService.deleteRolesForUser(userDTO);
        return ResponseEntity.ok("OKKK");
    }

    @RequestMapping(path = "/new-privileges-user", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewPrivilegesForUser(@RequestBody @Valid UserDTO userDTO){
        userService.saveNewPrivilegesForUser(userDTO);
        return ResponseEntity.ok("OKKK");
    }

    @RequestMapping(path = "/delete-privileges-user", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deletePrivilegesForUser(@RequestBody @Valid UserDTO userDTO){
        userService.deletePrivilegesForUser(userDTO);
        return ResponseEntity.ok("OKKK");
    }
}
