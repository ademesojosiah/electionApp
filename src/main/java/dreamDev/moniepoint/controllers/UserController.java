package dreamDev.moniepoint.controllers;

import dreamDev.moniepoint.dtos.requests.LoginRequest;
import dreamDev.moniepoint.dtos.requests.LogoutRequest;
import dreamDev.moniepoint.dtos.requests.UserRegistrationRequest;
import dreamDev.moniepoint.dtos.responses.ApiResponse;
import dreamDev.moniepoint.exceptions.ElectionException;
import dreamDev.moniepoint.exceptions.UserException;
import dreamDev.moniepoint.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> register(@RequestBody UserRegistrationRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.register(request)), HttpStatus.CREATED);
        } catch (ElectionException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.login(loginRequest.getEmail(), loginRequest.getPassword())), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{loginId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable("loginId") String loginId) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.getUser(loginId)), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody LogoutRequest logoutRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.logout(logoutRequest.getLoginId())), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}