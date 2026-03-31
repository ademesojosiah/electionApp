package dreamDev.moniepoint.controllers;

import dreamDev.moniepoint.dtos.requests.CastVoteRequest;
import dreamDev.moniepoint.dtos.responses.ApiResponse;
import dreamDev.moniepoint.exceptions.CandidateNotFoundException;
import dreamDev.moniepoint.exceptions.ElectionException;
import dreamDev.moniepoint.exceptions.UserException;
import dreamDev.moniepoint.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<ApiResponse> castVote(@RequestBody CastVoteRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, voteService.castVote(request)), HttpStatus.CREATED);
        } catch (UserException | CandidateNotFoundException | ElectionException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{electionId}")
    public ResponseEntity<ApiResponse> getVotesForElection(@PathVariable("electionId") String electionId) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, voteService.getVotesForElection(electionId)), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}