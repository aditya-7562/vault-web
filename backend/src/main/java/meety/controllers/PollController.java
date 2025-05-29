package meety.controllers;

import meety.dtos.PollDto;
import meety.exceptions.GroupNotFoundException;
import meety.models.Group;
import meety.models.Poll;
import meety.models.User;
import meety.services.GroupService;
import meety.services.PollService;
import meety.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups/{groupId}/polls")
public class PollController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private PollService pollService;

    @Autowired
    private AuthService authService;

    @PostMapping("")
    public ResponseEntity<Poll> createPoll(
            @PathVariable Long groupId,
            @RequestBody PollDto pollDto) {
        User currentUser = authService.getCurrentUser();
        Group group = groupService.getGroupById(groupId).orElseThrow(() -> new GroupNotFoundException("Group with id {groupId} not found"));
        Poll poll = pollService.createPoll(group, currentUser, pollDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(poll);
    }

    @GetMapping("")
    public ResponseEntity<List<PollDto>> getPolls(@PathVariable Long groupId) {
        User currentUser = authService.getCurrentUser();
        List<PollDto> polls = pollService.getPollsByGroup(groupId, currentUser).stream().map(PollDto::new).toList();
        return ResponseEntity.ok(polls);
    }
}
