package dreamDev.moniepoint.data.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Document
public class Vote {
    @Id
    private String id;
    private String userId;
    private String candidateId;
    private String electionId;
    private LocalDateTime createdAt = LocalDateTime.now();
}