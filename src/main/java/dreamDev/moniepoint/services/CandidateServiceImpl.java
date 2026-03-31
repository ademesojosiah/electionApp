package dreamDev.moniepoint.services;

import dreamDev.moniepoint.data.models.Candidate;
import dreamDev.moniepoint.data.models.Election;
import dreamDev.moniepoint.data.repositories.CandidateRepository;
import dreamDev.moniepoint.data.repositories.ElectionRepository;
import dreamDev.moniepoint.dtos.requests.AddCandidateRequest;
import dreamDev.moniepoint.dtos.responses.CandidateResponse;
import dreamDev.moniepoint.exceptions.CandidateNotFoundException;
import dreamDev.moniepoint.exceptions.ElectionNotFoundException;
import dreamDev.moniepoint.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static dreamDev.moniepoint.utils.Mapper.map;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private ElectionRepository electionRepository;

    @Override
    public CandidateResponse addCandidate(AddCandidateRequest request) {
        Election election = electionRepository.findById(request.getElectionId())
            .orElseThrow(() -> new ElectionNotFoundException("Election not found"));

        if(candidateRepository.existsByNameAndPartyAndElectionId(request.getName().trim(),request.getParty() , request.getElectionId())) {
            throw new CandidateNotFoundException("Candidate already exists");
        }

        if(election.getStatus().equals("CLOSE")) {
            throw new ElectionNotFoundException("Election is already closed");
        }

        Candidate saved = candidateRepository.save(map(request));
        return map(saved);
    }

    @Override
    public List<CandidateResponse> getCandidatesForElection(String electionId) {
        return candidateRepository.findByElectionId(electionId)
            .stream().map(Mapper::map).toList();
    }
}