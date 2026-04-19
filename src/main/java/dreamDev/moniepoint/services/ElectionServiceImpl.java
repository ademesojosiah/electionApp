package dreamDev.moniepoint.services;

import dreamDev.moniepoint.data.models.Election;
import dreamDev.moniepoint.data.repositories.ElectionRepository;
import dreamDev.moniepoint.dtos.requests.CreateElectionRequest;
import dreamDev.moniepoint.dtos.responses.ElectionResponse;
import dreamDev.moniepoint.enums.ElectionStatus;
import dreamDev.moniepoint.exceptions.ElectionException;
import dreamDev.moniepoint.exceptions.ElectionNotFoundException;
import dreamDev.moniepoint.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static dreamDev.moniepoint.utils.Mapper.map;

@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {
    private final ElectionRepository electionRepository;

    @Override
    public ElectionResponse createElection(CreateElectionRequest request) {
        if(electionRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new ElectionException("Election with title " + request.getTitle() + " already exists");
        }
        Election saved = electionRepository.save(map(request));
        return map(saved);
    }



    @Override
    public ElectionResponse endElection(String electionId) {
        Election election = electionRepository.findById(electionId)
            .orElseThrow(() -> new ElectionNotFoundException("Election not found"));
        election.setStatus(ElectionStatus.CLOSE.toString());
        return map(electionRepository.save(election));
    }

    @Override
    public List<ElectionResponse> getAllElections() {
        return electionRepository.findAll().stream().map(Mapper::map).toList();
    }
}