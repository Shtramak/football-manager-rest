package ua.procamp.footballmanager.service;

import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<Team> findAll();

    Optional<Team> findById(Long id);

    Team save(Team team);

    void update(Team team);

    void removeById(Long id);

    Player findCaptainByTeam(Team team);

    void addNewPlayerToTeam(Player player, Team team);

    void assignCaptainByTeam(Player player, Team team);
}
