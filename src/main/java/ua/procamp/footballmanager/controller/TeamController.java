package ua.procamp.footballmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.TeamDto;
import ua.procamp.footballmanager.exception.EntityNotFoundException;
import ua.procamp.footballmanager.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @GetMapping
    public List<TeamDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TeamDto findById(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> {
            String message = String.format("No team with id %d found", id);
            return new EntityNotFoundException(message);
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDto save(@RequestBody TeamDto teamDto) {
        return service.save(teamDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.removeById(id);
    }

    @GetMapping("/{id}/captain")
    public PlayerDto findCaptain(@PathVariable("id") Long id) {
        return service.findCaptainByTeam(id);
    }

    @PutMapping("/{teamId}/captain/{playerId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void assignCaptain(@PathVariable("teamId") Long teamId, @PathVariable("playerId") Long playerId) {
        service.assignCaptainByTeam(teamId, playerId);
    }

    @PostMapping("/{id}/player")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewPlayerToTeam(@PathVariable Long id, @RequestBody PlayerDto playerDto) {
        service.addNewPlayerToTeam(id, playerDto);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(EntityNotFoundException exception) {
        String message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
