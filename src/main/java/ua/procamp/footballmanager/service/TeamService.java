package ua.procamp.footballmanager.service;

import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;

public interface TeamService {
    List<Team> findAll();

    Player findCaptainByTeam(Team team);

    void addNewPlayerToTeam(Player player, Team team);

    void assignCaptainByTeam(Player player, Team team);
}
