package dreamDev.moniepoint.utils;



import dreamDev.moniepoint.data.models.Candidate;
import dreamDev.moniepoint.data.models.Election;
import dreamDev.moniepoint.data.models.User;
import dreamDev.moniepoint.data.models.Vote;
import dreamDev.moniepoint.dtos.requests.AddCandidateRequest;
import dreamDev.moniepoint.dtos.requests.CastVoteRequest;
import dreamDev.moniepoint.dtos.requests.CreateElectionRequest;
import dreamDev.moniepoint.dtos.requests.UserRegistrationRequest;
import dreamDev.moniepoint.dtos.responses.*;
import dreamDev.moniepoint.enums.Role;


public class Mapper {
    public static User map(UserRegistrationRequest userRegistrationRequest) {
        User user = new User();
        user.setFirstName(userRegistrationRequest.getFirstName());
        user.setLastName(userRegistrationRequest.getLastName());
        user.setEmail(userRegistrationRequest.getEmail());
        user.setPassword(userRegistrationRequest.getPassword());
        user.setRole(Role.USER.name());
        return user;
    }

    public static UserRegistrationResponse map(User savedUser) {
        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
        userRegistrationResponse.setEmail(savedUser.getEmail());
        userRegistrationResponse.setId(savedUser.getId());
        userRegistrationResponse.setRole(savedUser.getRole());
        return userRegistrationResponse;
    }

    public static LoginResponse map(User user, String loginId) {
        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setLoginId(loginId);
        response.setMessage("Login successful");
        return response;
    }

    public static Vote map(CastVoteRequest req) {
        Vote vote = new Vote();
        vote.setUserId(req.getUserId());
        vote.setCandidateId(req.getCandidateId());
        vote.setElectionId(req.getElectionId());
        return vote;
    }

    public static VoteResponse mapToVoteResponse(Vote vote) {
        VoteResponse res = new VoteResponse();
        res.setVoteId(vote.getId());
        res.setCandidateId(vote.getCandidateId());
        res.setElectionId(vote.getElectionId());
        res.setCreatedAt(vote.getCreatedAt());
        return res;
    }

    public static Election map(CreateElectionRequest req) {
        Election election = new Election();
        election.setTitle(req.getTitle());
        election.setStatus("OPEN");
        return election;
    }

    public static ElectionResponse map(Election election) {
        ElectionResponse res = new ElectionResponse();
        res.setId(election.getId());
        res.setTitle(election.getTitle());
        res.setStatus(election.getStatus());
        return res;
    }

    // Candidate mappers
    public static Candidate map(AddCandidateRequest req) {
        Candidate candidate = new Candidate();
        candidate.setName(req.getName().trim());
        candidate.setParty(req.getParty());
        candidate.setElectionId(req.getElectionId());
        candidate.setVoteCount(0);
        return candidate;
    }

    public static CandidateResponse map(Candidate candidate) {
        CandidateResponse res = new CandidateResponse();
        res.setId(candidate.getId());
        res.setName(candidate.getName());
        res.setParty(candidate.getParty());
        res.setVoteCount(candidate.getVoteCount());
        res.setElectionId(candidate.getElectionId());
        return res;
    }
}
