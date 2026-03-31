package dreamDev.moniepoint.services;

import dreamDev.moniepoint.dtos.requests.UserRegistrationRequest;
import dreamDev.moniepoint.dtos.responses.LoginResponse;
import dreamDev.moniepoint.dtos.responses.UserRegistrationResponse;

public interface UserService {
    UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest);

    LoginResponse login(String email, String password);
}
