package ua.procamp.footballmanager.service;

import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.TeamDto;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<TeamDto> findAll();

    Optional<TeamDto> findById(Long id);

    Team save(TeamDto teamDto);

    void update(TeamDto teamDto);

    void removeById(Long id);

    Player findCaptainByTeam(TeamDto teamDto);

    void addNewPlayerToTeam(PlayerDto playerDto, TeamDto teamDto);

    void assignCaptainByTeam(PlayerDto player, TeamDto teamDto);
}
