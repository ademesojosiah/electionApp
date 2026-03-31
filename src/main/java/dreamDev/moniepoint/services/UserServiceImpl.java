package dreamDev.moniepoint.services;

import dreamDev.moniepoint.data.models.User;
import dreamDev.moniepoint.data.repositories.UserRepository;
import dreamDev.moniepoint.dtos.requests.UserRegistrationRequest;
import dreamDev.moniepoint.dtos.responses.LoginResponse;
import dreamDev.moniepoint.dtos.responses.LogoutResponse;
import dreamDev.moniepoint.dtos.responses.UserRegistrationResponse;
import dreamDev.moniepoint.exceptions.DuplicateUserException;
import dreamDev.moniepoint.exceptions.InvalidCredentialsException;
import dreamDev.moniepoint.exceptions.UserNotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static dreamDev.moniepoint.utils.Mapper.map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new DuplicateUserException("User with email " + request.getEmail() + " already exists");
        User savedUser = userRepository.save(map(request));
        return map(savedUser);
    }

    @Override
    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!user.getPassword().equals(password))
            throw new InvalidCredentialsException("Invalid email or password");

        String loginId = UUID.randomUUID().toString();
        user.setLoginId(loginId);
        userRepository.save(user);

        return map(user, loginId);
    }

    @Override
    public LogoutResponse logout(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotLoggedInException("No active session found for this loginId"));

        user.setLoginId(null);
        userRepository.save(user);

        LogoutResponse response = new LogoutResponse();
        response.setMessage("Logout successful");
        return response;
    }
}