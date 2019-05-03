package ua.procamp.footballmanager.repository;

import ua.procamp.footballmanager.entity.Player;

public interface CustomTeamRepository {
    void addNewPlayerToTeam(Long teamId, Player player);

    void assignCaptainByTeam(Long teamId, Long playerId);
}
