package ua.procamp.footballmanager.service;

import ua.procamp.footballmanager.dto.PlayerDto;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<PlayerDto> findAll();

    Optional<PlayerDto> findById(Long id);

    PlayerDto save(PlayerDto playerDto);

    void update(PlayerDto playerDto);

    void removeById(Long id);

    List<PlayerDto> findPlayersByTeam(Long teamId);
}
