package dreamDev.moniepoint.services;

import dreamDev.moniepoint.data.models.Candidate;
import dreamDev.moniepoint.data.repositories.CandidateRepository;
import dreamDev.moniepoint.data.repositories.ElectionRepository;
import dreamDev.moniepoint.data.repositories.UserRepository;
import dreamDev.moniepoint.data.repositories.VoteRepository;
import dreamDev.moniepoint.dtos.requests.AddCandidateRequest;
import dreamDev.moniepoint.dtos.requests.CastVoteRequest;
import dreamDev.moniepoint.dtos.requests.CreateElectionRequest;
import dreamDev.moniepoint.dtos.requests.UserRegistrationRequest;
import dreamDev.moniepoint.dtos.responses.*;
import dreamDev.moniepoint.exceptions.ElectionException;
import dreamDev.moniepoint.exceptions.ElectionNotActiveException;
import dreamDev.moniepoint.exceptions.UserNotLoggedInException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoteServiceImplTest {
    @Autowired
    private VoteService voteService;
    @Autowired
    private ElectionService electionService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private ElectionRepository electionRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private UserRepository userRepository;

    private String loginId, electionId, candidateId;

    @BeforeEach
    void setUp() {
        voteRepository.deleteAll();
        candidateRepository.deleteAll();
        electionRepository.deleteAll();
        userRepository.deleteAll();

        UserRegistrationRequest userReq = new UserRegistrationRequest();
        userReq.setEmail("voter@mail.com");
        userReq.setFirstName("Jane");
        userReq.setLastName("Doe");
        userReq.setPassword("pass123");
        userService.register(userReq);
        LoginResponse loginRes = userService.login("voter@mail.com", "pass123");
        loginId = loginRes.getLoginId();

        CreateElectionRequest electionReq = new CreateElectionRequest();
        electionReq.setTitle("Test Election");
        ElectionResponse electionRes = electionService.createElection(electionReq);
        electionId = electionRes.getId();

        AddCandidateRequest candidateReq = new AddCandidateRequest();
        candidateReq.setName("Candidate A");
        candidateReq.setParty("APC");
        candidateReq.setElectionId(electionId);
        CandidateResponse candidateRes = candidateService.addCandidate(candidateReq);
        candidateId = candidateRes.getId();
    }

    @Test
    void castVote_successfullySavesVoteTest() {
        CastVoteRequest req = voteRequest();
        VoteResponse response = voteService.castVote(req);

        assertNotNull(response.getVoteId());
        assertEquals(candidateId, response.getCandidateId());
    }

    @Test
    void castVote_incrementsCandidateVoteCountTest() {
        Candidate oldCandidate = candidateRepository.findById(candidateId).get();
        assertEquals(0,oldCandidate.getVoteCount());
        voteService.castVote(voteRequest());
        Candidate candidate = candidateRepository.findById(candidateId).get();
        assertEquals(1, candidate.getVoteCount());
    }

    @Test
    void castVoteTwice_throwElectionExceptionTest() {
        voteService.castVote(voteRequest());
        assertThrows(ElectionException.class,()-> voteService.castVote(voteRequest()));

    }

    @Test
    void castVote_withoutLogin_throwsException() {
        CastVoteRequest req = voteRequest();
        req.setLoginId("fake-login-id");
        assertThrows(UserNotLoggedInException.class, () -> voteService.castVote(req));
    }

    @Test
    void castVote_onEndedElection_throwsException() {
        electionService.endElection(electionId);
        assertThrows(ElectionNotActiveException.class, () -> voteService.castVote(voteRequest()));
    }

    private CastVoteRequest voteRequest() {
        CastVoteRequest req = new CastVoteRequest();
        req.setLoginId(loginId);
        req.setCandidateId(candidateId);
        req.setElectionId(electionId);
        return req;
    }
}