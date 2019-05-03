package ua.procamp.footballmanager.service;

import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.TeamDto;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<TeamDto> findAll();

    Optional<TeamDto> findById(Long id);

    TeamDto save(TeamDto teamDto);

    void removeById(Long id);

    PlayerDto findCaptainByTeam(Long teamId);

    void addNewPlayerToTeam(Long teamId, PlayerDto playerDto);

    void assignCaptainByTeam(Long teamId, Long playerId);
}
