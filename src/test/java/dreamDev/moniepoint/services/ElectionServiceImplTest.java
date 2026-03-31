package dreamDev.moniepoint.services;

import dreamDev.moniepoint.data.repositories.ElectionRepository;
import dreamDev.moniepoint.dtos.requests.CreateElectionRequest;
import dreamDev.moniepoint.dtos.responses.ElectionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ElectionServiceImplTest {
    @Autowired
    private ElectionService electionService;
    @Autowired
    private ElectionRepository electionRepository;
    private CreateElectionRequest createRequest;

    @BeforeEach
    void setUp() {
        electionRepository.deleteAll();
        createRequest = new CreateElectionRequest();
        createRequest.setTitle("Presidential Election 2025");
    }


    @Test
    void startElection_electionStatusIsOPENTest() {
        ElectionResponse created = electionService.createElection(createRequest);
        assertEquals("OPEN", created.getStatus());
    }

    @Test
    void endElection_changesStatusToCloseTest() {
        ElectionResponse created = electionService.createElection(createRequest);
        ElectionResponse ended = electionService.endElection(created.getId());
        assertEquals("CLOSE", ended.getStatus());
    }
}