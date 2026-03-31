package dreamDev.moniepoint.data.repositories;

import dreamDev.moniepoint.data.models.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoteRepository extends MongoRepository<Vote, String> {
    List<Vote> findByElectionId(String electionId);
    List<Vote> findByUserId(String userId);
    boolean existsByUserIdAndCandidateIdAndElectionId( String userId,String candidateId, String electionId);

}