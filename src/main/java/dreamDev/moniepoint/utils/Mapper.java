package dreamDev.moniepoint.utils;



import dreamDev.moniepoint.data.models.User;
import dreamDev.moniepoint.dtos.requests.UserRegistrationRequest;
import dreamDev.moniepoint.dtos.responses.LoginResponse;
import dreamDev.moniepoint.dtos.responses.UserRegistrationResponse;
import dreamDev.moniepoint.enums.Role;


public class Mapper {
    public static User map(UserRegistrationRequest userRegistrationRequest) {
        User user = new User();
        user.setFirstName(userRegistrationRequest.getFirstName());
        user.setLastName(userRegistrationRequest.getLastName());
        user.setEmail(userRegistrationRequest.getEmail());
        user.setPassword(userRegistrationRequest.getPassword());
        user.setRole(Role.USER.name());
        return user;
    }

    public static UserRegistrationResponse map(User savedUser) {
        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
        userRegistrationResponse.setEmail(savedUser.getEmail());
        userRegistrationResponse.setId(savedUser.getId());
        userRegistrationResponse.setRole(savedUser.getRole());
        return userRegistrationResponse;
    }

    public static LoginResponse map(User user, String loginId) {
        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setLoginId(loginId);
        response.setMessage("Login successful");
        return response;
    }
}
