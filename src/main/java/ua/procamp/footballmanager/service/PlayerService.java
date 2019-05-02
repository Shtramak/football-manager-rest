package ua.procamp.footballmanager.service;

import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<Player> findAll();

    Optional<Player> findById(Long id);

    Player save(Player player);

    void update(Player player);

    void removeById(Long id);

    List<Player> findPlayersByTeam(Team team);
}
