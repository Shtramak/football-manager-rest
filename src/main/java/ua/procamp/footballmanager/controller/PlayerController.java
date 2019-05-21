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
import ua.procamp.footballmanager.exception.EntityNotFoundException;
import ua.procamp.footballmanager.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlayerDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PlayerDto findById(@PathVariable long id) {
        return service.findById(id).orElseThrow(() -> {
            String message = String.format("No player with id %d found", id);
            return new EntityNotFoundException(message);
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDto save(@RequestBody PlayerDto playerDto) {
        return service.save(playerDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@RequestBody PlayerDto playerDto) {
        service.update(playerDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }

    @GetMapping("/team/{teamId}")
    public List<PlayerDto> findPlayersByTeam(@PathVariable long teamId) {
        return service.findPlayersByTeam(teamId);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(EntityNotFoundException exception) {
        String message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

}
