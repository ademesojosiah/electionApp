package dreamDev.moniepoint.services;


import dreamDev.moniepoint.data.models.User;
import dreamDev.moniepoint.data.repositories.UserRepository;
import dreamDev.moniepoint.dtos.requests.UserRegistrationRequest;
import dreamDev.moniepoint.dtos.responses.LoginResponse;
import dreamDev.moniepoint.dtos.responses.UserRegistrationResponse;
import dreamDev.moniepoint.exceptions.DuplicateUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dreamDev.moniepoint.utils.Mapper.map;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest) {
        if(userRepository.findByEmail(userRegistrationRequest.getEmail()).isPresent()) throw new DuplicateUserException("User with email " + userRegistrationRequest.getEmail() + " already exists");
        User savedUser =  userRepository.save(map(userRegistrationRequest));
        return map(savedUser);
    }

    @Override
    public LoginResponse login(String email, String password) {
        return null;
    }
}
