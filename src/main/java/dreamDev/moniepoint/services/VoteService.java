package dreamDev.moniepoint.services;

import dreamDev.moniepoint.dtos.requests.CastVoteRequest;
import dreamDev.moniepoint.dtos.responses.VoteResponse;

import java.util.List;

public interface VoteService {
    VoteResponse castVote(CastVoteRequest request);
    List<VoteResponse> getVotesForElection(String electionId);
}