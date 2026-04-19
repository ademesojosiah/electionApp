package dreamDev.moniepoint.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VoteResponse {
    private String id;
    private String candidateId;
    private String electionId;
    private LocalDateTime createdAt;
}