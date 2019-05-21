package ua.procamp.footballmanager.service;

import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.TeamDto;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<TeamDto> findAll();

    Optional<TeamDto> findById(long id);

    TeamDto save(TeamDto teamDto);

    void removeById(long id);

    PlayerDto findCaptainByTeam(long teamId);

    void addNewPlayerToTeam(long teamId, PlayerDto playerDto);

    void assignCaptainByTeam(long teamId, long playerId);
}
