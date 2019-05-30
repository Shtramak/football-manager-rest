package ua.procamp.footballmanager.repository;

import ua.procamp.footballmanager.entity.Player;

public interface CustomTeamRepository {
    void addNewPlayerToTeam(long teamId, Player player);

    void assignCaptainByTeam(long teamId, long playerId);

    void removeById(long teamId);
}
