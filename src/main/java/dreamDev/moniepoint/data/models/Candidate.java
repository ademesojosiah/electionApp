package dreamDev.moniepoint.data.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Candidate {
    @Id
    private String id;

    private String name;

    private String party;

    private Integer voteCount;

    private String electionId;
}

