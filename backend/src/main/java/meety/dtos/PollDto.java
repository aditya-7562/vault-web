package meety.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meety.models.Poll;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollDto {
    private String question;
    private Date deadline;
    private boolean isAnonymous;

    public PollDto(Poll poll) {
        question = poll.getQuestion();
        deadline = poll.getDeadline();
        isAnonymous = poll.isAnonymous();
    }
}
