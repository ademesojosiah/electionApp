package dreamDev.moniepoint.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoteResponse {
    private String voteId;
    private String candidateId;
    private String electionId;
    private LocalDateTime createdAt;
}