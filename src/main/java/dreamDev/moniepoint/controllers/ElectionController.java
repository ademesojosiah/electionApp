package dreamDev.moniepoint.controllers;

import dreamDev.moniepoint.dtos.requests.CreateElectionRequest;
import dreamDev.moniepoint.dtos.requests.UpdateElectionRequest;
import dreamDev.moniepoint.dtos.responses.ApiResponse;
import dreamDev.moniepoint.exceptions.ElectionException;
import dreamDev.moniepoint.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/election")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @PostMapping
    public ResponseEntity<ApiResponse> createElection(@RequestBody CreateElectionRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, electionService.createElection(request)), HttpStatus.CREATED);
        } catch (ElectionException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("")
    public ResponseEntity<ApiResponse> endElection(@RequestBody UpdateElectionRequest updateElectionRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, electionService.endElection(updateElectionRequest.getElectionId())), HttpStatus.OK);
        } catch (ElectionException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllElections() {
        try {
            return new ResponseEntity<>(new ApiResponse(true, electionService.getAllElections()), HttpStatus.OK);
        } catch (ElectionException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}