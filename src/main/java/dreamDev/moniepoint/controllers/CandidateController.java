package dreamDev.moniepoint.controllers;

import dreamDev.moniepoint.dtos.requests.AddCandidateRequest;
import dreamDev.moniepoint.dtos.responses.ApiResponse;
import dreamDev.moniepoint.exceptions.CandidateNotFoundException;
import dreamDev.moniepoint.exceptions.ElectionException;
import dreamDev.moniepoint.exceptions.UserException;
import dreamDev.moniepoint.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public ResponseEntity<ApiResponse> addCandidate(@RequestBody AddCandidateRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, candidateService.addCandidate(request)), HttpStatus.CREATED);
        } catch (UserException | ElectionException | CandidateNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{electionId}")
    public ResponseEntity<ApiResponse> getCandidatesForElection(@PathVariable("electionId") String electionId) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, candidateService.getCandidatesForElection(electionId)), HttpStatus.OK);
        } catch (UserException | ElectionException | CandidateNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}