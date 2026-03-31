package dreamDev.moniepoint.services;

import dreamDev.moniepoint.dtos.requests.UserRegistrationRequest;
import dreamDev.moniepoint.dtos.responses.LoginResponse;
import dreamDev.moniepoint.dtos.responses.LogoutResponse;
import dreamDev.moniepoint.dtos.responses.UserRegistrationResponse;

public interface UserService {
    UserRegistrationResponse register(UserRegistrationRequest request);
    LoginResponse login(String email, String password);
    LogoutResponse logout(String loginId);
}
