package controllers;

import controllers.config.EndpointUtils;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import services.UserService;

@RestController
@RequestMapping(path = EndpointUtils.API_VERSION + "/users")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value="/addUser",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.registerUser(user);
        return new ResponseEntity<>(user, null, HttpStatus.OK);
    }
}
