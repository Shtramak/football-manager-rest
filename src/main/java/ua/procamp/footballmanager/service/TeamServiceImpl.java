package ua.procamp.footballmanager.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.PlayerMapper;
import ua.procamp.footballmanager.dto.TeamDto;
import ua.procamp.footballmanager.dto.TeamMapper;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;
import ua.procamp.footballmanager.exception.EntityNotFoundException;
import ua.procamp.footballmanager.repository.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private final TeamRepository repository;

    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamDto> findAll() {
        return TeamMapper.listTeamToListTeamDto(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TeamDto> findById(Long id) {
        return repository.findById(id).map(TeamMapper::teamToTeamDto);
    }

    @Override
    public TeamDto save(TeamDto teamDto) {
        Team team = TeamMapper.teamDtoToTeam(teamDto);
        return TeamMapper.teamToTeamDto(repository.save(team));
    }

    @Override
    public void removeById(Long id) {
        Team team = repository.findById(id).orElseThrow(() -> {
            String message = String.format("No team with id %d found", id);
            return new EntityNotFoundException(message);
        });
        Set<Player> players = team.getPlayers();
        players.forEach(player -> player.setTeam(null));
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerDto findCaptainByTeam(Long teamId) {
        return repository
                .findCaptain(teamId)
                .map(PlayerMapper::playerToPlayerDto)
                .orElseThrow(() -> {
                    String message = String.format("No captain for team with id %d found", teamId);
                    return new EntityNotFoundException(message);
                });
    }

    @Override
    public void addNewPlayerToTeam(Long teamId, PlayerDto playerDto) {
        Player player = PlayerMapper.playerDtoToPlayer(playerDto);
        repository.addNewPlayerToTeam(teamId, player);
    }

    @Override
    public void assignCaptainByTeam(Long teamId, Long playerId) {
        repository.assignCaptainByTeam(teamId, playerId);
    }
}
