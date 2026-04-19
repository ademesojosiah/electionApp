package dreamDev.moniepoint.data.repositories;

import dreamDev.moniepoint.data.models.Election;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ElectionRepository extends MongoRepository<Election, String> {
    boolean existsByTitleIgnoreCase(String id);
    Optional<Election> findByTitle(String title);
    List<Election> findByStatus(String status);
}