package dreamDev.moniepoint.dtos.responses;


import lombok.Data;

@Data
public class LoginResponse {
    private String userId;
    private String loginId;
    private String message;
}
