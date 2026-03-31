package dreamDev.moniepoint.services;

import dreamDev.moniepoint.dtos.requests.CreateElectionRequest;
import dreamDev.moniepoint.dtos.responses.ElectionResponse;

import java.util.List;

public interface ElectionService {
    ElectionResponse createElection(CreateElectionRequest request);
    ElectionResponse endElection(String electionId);
    List<ElectionResponse> getAllElections();
}
