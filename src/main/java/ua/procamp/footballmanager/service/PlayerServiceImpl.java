package ua.procamp.footballmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.PlayerMapper;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.exception.EntityNotFoundException;
import ua.procamp.footballmanager.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository repository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerDto> findAll() {
        return PlayerMapper.listPlayerToListPlayerDto(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlayerDto> findById(Long id) {
        return repository.findById(id).map(PlayerMapper::playerToPlayerDto);
    }

    @Override
    public PlayerDto save(PlayerDto playerDto) {
        Player player = PlayerMapper.playerDtoToPlayer(playerDto);
        return PlayerMapper.playerToPlayerDto(repository.save(player));
    }

    @Override
    public void update(PlayerDto playerDto) {
        Player player = PlayerMapper.playerDtoToPlayer(playerDto);
        Player managedPlayer = repository.findById(player.getId())
                .orElseThrow(() -> {
                    String message = String.format("Player with id %d not found", playerDto.getPlayerId());
                    return new EntityNotFoundException(message);
                });
        managedPlayer.setFirstName(player.getFirstName());
        managedPlayer.setLastName(player.getLastName());
        managedPlayer.setPosition(player.getPosition());
        managedPlayer.setBirthday(player.getBirthday());
        managedPlayer.setTeam(player.getTeam());
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerDto> findPlayersByTeam(Long teamId) {
        return PlayerMapper.listPlayerToListPlayerDto(repository.findByTeam(teamId));
    }
}
