package ua.procamp.footballmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.PlayerMapper;
import ua.procamp.footballmanager.dto.TeamDto;
import ua.procamp.footballmanager.dto.TeamMapper;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;
import ua.procamp.footballmanager.repository.PlayerRepository;

import java.util.List;
import java.util.NoSuchElementException;
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
        Player managedPlayer = repository.findById(player.getId()).orElseThrow(NoSuchElementException::new);
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
    public List<PlayerDto> findPlayersByTeam(TeamDto teamDto) {
        Team team = TeamMapper.teamDtoToTeam(teamDto);
        return PlayerMapper.listPlayerToListPlayerDto(repository.findByTeam(team.getId()));
    }
}
