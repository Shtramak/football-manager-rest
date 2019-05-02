package ua.procamp.footballmanager.dao;

import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

public interface CustomTeamRepository {
    void addNewPlayerToTeam(Player player, Team team);

    void assignCaptainByTeam(Player player, Team team);
}
