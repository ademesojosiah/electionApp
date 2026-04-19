package dreamDev.moniepoint.services;

import dreamDev.moniepoint.data.models.Election;
import dreamDev.moniepoint.data.repositories.ElectionRepository;
import dreamDev.moniepoint.dtos.requests.CreateElectionRequest;
import dreamDev.moniepoint.dtos.responses.ElectionResponse;
import dreamDev.moniepoint.enums.ElectionStatus;
import dreamDev.moniepoint.exceptions.ElectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ElectionServiceImplTest {
    private CreateElectionRequest createRequest;

    private Election savedElection;

    @BeforeEach
    void setUp() {
        createRequest = new CreateElectionRequest();
        createRequest.setTitle("Presidential Election 2025");

        savedElection = new Election();
        savedElection.setTitle(createRequest.getTitle());
        savedElection.setId("xyz");
        savedElection.setStatus(ElectionStatus.OPEN.toString());
    }

    @Test
    void createElection_withAnExistingTitle_throwsElectionExceptionTest() {
        ElectionRepository electionRepository = Mockito.mock(ElectionRepository.class);
        ElectionService electionService = new ElectionServiceImpl(electionRepository);

        Mockito.when(electionRepository.existsByTitleIgnoreCase(createRequest.getTitle())).thenReturn(true);

        assertThrows(ElectionException.class,()-> electionService.createElection(createRequest));
    }

    @Test
    void startElection_electionStatusIsOPENTest() {

        ElectionRepository electionRepository = Mockito.mock(ElectionRepository.class);
        ElectionService electionService = new ElectionServiceImpl(electionRepository);

        Mockito.when(electionRepository.existsByTitleIgnoreCase(createRequest.getTitle())).thenReturn(false);
        Mockito.when(electionRepository.save(Mockito.any(Election.class))).thenReturn(savedElection);
        ElectionResponse created = electionService.createElection(createRequest);
        assertEquals(ElectionStatus.OPEN.toString(), created.getStatus());
    }

    @Test
    void endElection_changesStatusToCloseTest() {

        String electionId = savedElection.getId();

        ElectionRepository electionRepository = Mockito.mock(ElectionRepository.class);
        ElectionService electionService = new ElectionServiceImpl(electionRepository);
        Mockito.when(electionRepository.findById(electionId)).thenReturn(Optional.of(savedElection));
        savedElection.setStatus(ElectionStatus.CLOSE.toString());
        Mockito.when(electionRepository.save(Mockito.any())).thenReturn(savedElection);


        ElectionResponse ended = electionService.endElection(electionId);
        assertEquals(ElectionStatus.CLOSE.toString(), ended.getStatus());
    }
}