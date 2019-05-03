package ua.procamp.footballmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.procamp.footballmanager.entity.Team;
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
    public List<Team> findAll(){
        return service.findAll();
    }
}
