package meety.services;

import meety.dtos.PollDto;
import meety.exceptions.GroupNotFoundException;
import meety.exceptions.UnauthorizedException;
import meety.models.Group;
import meety.models.Poll;
import meety.models.User;
import meety.repositories.GroupMemberRepository;
import meety.repositories.GroupRepository;
import meety.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public Poll createPoll(Group group, User author, PollDto pollDto) {
        if (groupMemberRepository.findByGroupAndUser(group, author).isEmpty()) {
            throw new UnauthorizedException("User is not a member of the group");
        }
        Poll poll = Poll.builder()
                .group(group)
                .author(author)
                .question(pollDto.getQuestion())
                .deadline(pollDto.getDeadline())
                .isAnonymous(pollDto.isAnonymous())
                .build();
        return pollRepository.save(poll);
    }

    public List<Poll> getPollsByGroup(Long groupId, User currentUser) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group with id {groupId} not found"));
        if (groupMemberRepository.findByGroupAndUser(group, currentUser).isEmpty()) {
            throw new UnauthorizedException("User is not a member of the group");
        }

        return pollRepository.findByGroupId(groupId);
    }
}
