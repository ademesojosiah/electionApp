package dreamDev.moniepoint.services;

import dreamDev.moniepoint.data.repositories.UserRepository;
import dreamDev.moniepoint.dtos.requests.UserRegistrationRequest;
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
        registrationRequest.setEmail("email");
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
}