package ua.procamp.footballmanager.service;

import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;

public interface PlayerService {
    List<Player> findAll();

    List<Player> findPlayersByTeam(Team team);
}
