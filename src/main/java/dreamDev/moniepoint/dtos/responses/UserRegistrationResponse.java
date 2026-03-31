package dreamDev.moniepoint.dtos.responses;


import lombok.Data;

@Data
public class UserRegistrationResponse {
    private String id;
    private String email;
    private String role;
}
