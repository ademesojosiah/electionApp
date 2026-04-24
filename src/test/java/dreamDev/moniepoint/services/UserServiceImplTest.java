package dreamDev.moniepoint.services;

import dreamDev.moniepoint.data.repositories.UserRepository;
import dreamDev.moniepoint.dtos.requests.UserRegistrationRequest;
import dreamDev.moniepoint.dtos.responses.LoginResponse;
import dreamDev.moniepoint.dtos.responses.UserRegistrationResponse;
import dreamDev.moniepoint.exceptions.DuplicateUserException;
import dreamDev.moniepoint.exceptions.InvalidCredentialsException;
import dreamDev.moniepoint.exceptions.UserNotLoggedInException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    private UserRegistrationRequest registrationRequest;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        registrationRequest = new UserRegistrationRequest();
        registrationRequest.setEmail("jojo@mail.com");
        registrationRequest.setFirstName("firstName");
        registrationRequest.setLastName("lastName");
        registrationRequest.setPassword("password");

    }


    @Test
    void registerUser_userExistsTest() {
        assertFalse(userRepository.findByEmail(registrationRequest.getEmail()).isPresent());
        userService.register(registrationRequest);
        assertTrue(userRepository.findByEmail(registrationRequest.getEmail()).isPresent());
    }

    @Test
    void registerUser_withDuplicateEmail_throwsExceptionTest() {
        userService.register(registrationRequest);
        assertThrows(DuplicateUserException.class, () -> userService.register(registrationRequest));
    }

    @Test
    void registerUser_getUser_returnsUserTest() {
        userService.register(registrationRequest);
        LoginResponse loginResponse = userService.login(registrationRequest.getEmail(), registrationRequest.getPassword());
        UserRegistrationResponse userRegistrationResponse = userService.getUser(loginResponse.getLoginId());

        assertEquals(registrationRequest.getEmail(), userRegistrationResponse.getEmail());
    }

    @Test
    void login_validCredentials_returnsLoginIdAndUserIdTest() {
        userService.register(registrationRequest);
        LoginResponse response = userService.login("jojo@mail.com", "password");

        assertNotNull(response.getLoginId());
        assertNotNull(response.getUserId());
        assertEquals("Login successful", response.getMessage());
    }


    @Test
    void login_invalidPassword_throwsException() {
        userService.register(registrationRequest);
        assertThrows(InvalidCredentialsException.class,
                () -> userService.login("jojo@mail.com", "wrongPass"));
    }


    @Test
    void logout_withInvalidLoginId_throwsException() {
        assertThrows(UserNotLoggedInException.class,
                () -> userService.logout("invalid-login-id"));
    }
}