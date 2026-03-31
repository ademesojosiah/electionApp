package dreamDev.moniepoint.dtos.requests;

import lombok.Data;

@Data
public class CastVoteRequest {
    private String candidateId;
    private String electionId;
    private String loginId;
    private String userId;
}