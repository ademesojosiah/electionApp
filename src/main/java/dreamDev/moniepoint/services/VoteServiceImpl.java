package dreamDev.moniepoint.services;

import dreamDev.moniepoint.data.models.Candidate;
import dreamDev.moniepoint.data.models.Election;
import dreamDev.moniepoint.data.models.User;
import dreamDev.moniepoint.data.models.Vote;
import dreamDev.moniepoint.data.repositories.CandidateRepository;
import dreamDev.moniepoint.data.repositories.ElectionRepository;
import dreamDev.moniepoint.data.repositories.UserRepository;
import dreamDev.moniepoint.data.repositories.VoteRepository;
import dreamDev.moniepoint.dtos.requests.CastVoteRequest;
import dreamDev.moniepoint.dtos.responses.VoteResponse;
import dreamDev.moniepoint.exceptions.*;
import dreamDev.moniepoint.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static dreamDev.moniepoint.utils.Mapper.map;
import static dreamDev.moniepoint.utils.Mapper.mapToVoteResponse;

@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public VoteResponse castVote(CastVoteRequest request) {

        if(request.getLoginId() == null || request.getLoginId().isEmpty()) {
            throw new UserNotLoggedInException("User is not logged in");
        }
        User user = userRepository.findByLoginId(request.getLoginId())
            .orElseThrow(() -> new UserNotLoggedInException("User not found"));

        Election election = electionRepository.findById(request.getElectionId())
            .orElseThrow(() -> new ElectionNotFoundException("Election not found"));
        if (!"OPEN".equals(election.getStatus()))
            throw new ElectionNotActiveException("Election is not currently active");

        if(voteRepository.existsByUserIdAndCandidateIdAndElectionId(user.getId(), request.getCandidateId(), request.getElectionId())){
            throw new ElectionException("Vote has be casted");
        }

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
            .orElseThrow(() -> new CandidateNotFoundException("Candidate not found"));
        if (!candidate.getElectionId().equals(request.getElectionId()))
            throw new CandidateNotFoundException("Candidate does not belong to this election");

        request.setUserId(user.getId());
        Vote savedVote = voteRepository.save(map(request));

        candidate.setVoteCount(candidate.getVoteCount() + 1);
        candidateRepository.save(candidate);

        return mapToVoteResponse(savedVote);
    }

    @Override
    public List<VoteResponse> getVotesForElection(String electionId) {
        return voteRepository.findByElectionId(electionId)
            .stream().map(Mapper::mapToVoteResponse).toList();
    }

}