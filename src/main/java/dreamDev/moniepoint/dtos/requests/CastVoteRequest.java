package dreamDev.moniepoint.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CastVoteRequest {
    private String candidateId;
    private String electionId;
    private String loginId;
    private String userId;
}