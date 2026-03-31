package dreamDev.moniepoint.services;

import dreamDev.moniepoint.dtos.requests.AddCandidateRequest;
import dreamDev.moniepoint.dtos.responses.CandidateResponse;

import java.util.List;

public interface CandidateService {
    CandidateResponse addCandidate(AddCandidateRequest request);

    List<CandidateResponse> getCandidatesForElection(String electionId);
}