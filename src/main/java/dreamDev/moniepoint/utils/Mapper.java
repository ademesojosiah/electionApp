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
import org.modelmapper.ModelMapper;


public class Mapper {


    public static User map(UserRegistrationRequest userRegistrationRequest) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userRegistrationRequest, User.class);
    }

    public static UserRegistrationResponse map(User savedUser) {
        return new ModelMapper().map(savedUser, UserRegistrationResponse.class);
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
        return new ModelMapper().map(vote, VoteResponse.class);
    }

    public static Election map(CreateElectionRequest req) {
        Election election = new Election();
        election.setTitle(req.getTitle());
        election.setStatus("OPEN");
        return election;
    }

    public static ElectionResponse map(Election election) {
        return new ModelMapper().map(election, ElectionResponse.class);
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
        return new ModelMapper().map(candidate,CandidateResponse.class);
    }
}
