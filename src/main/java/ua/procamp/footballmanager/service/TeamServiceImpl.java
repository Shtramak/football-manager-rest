package ua.procamp.footballmanager.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.dao.TeamRepository;
import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.PlayerMapper;
import ua.procamp.footballmanager.dto.TeamDto;
import ua.procamp.footballmanager.dto.TeamMapper;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public Team save(TeamDto teamDto) {
        return repository.save(TeamMapper.teamDtoToTeam(teamDto));
    }

    @Override
    public void update(TeamDto teamDto) {
        Team team = TeamMapper.teamDtoToTeam(teamDto);
        Team managedTeam = repository.findById(team.getId()).orElseThrow(NoSuchElementException::new);
        managedTeam.setName(team.getName());
        managedTeam.setCaptain(team.getCaptain());
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Player findCaptainByTeam(TeamDto teamDto) {
        return repository.findCaptain(teamDto.getTeamId()).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void addNewPlayerToTeam(PlayerDto playerDto, TeamDto teamDto) {
        Player player = PlayerMapper.playerDtoToPlayer(playerDto);
        Team team = TeamMapper.teamDtoToTeam(teamDto);
        repository.addNewPlayerToTeam(player, team);
    }

    @Override
    public void assignCaptainByTeam(PlayerDto playerDto, TeamDto teamDto) {
        Player player = PlayerMapper.playerDtoToPlayer(playerDto);
        Team team = TeamMapper.teamDtoToTeam(teamDto);
        repository.assignCaptainByTeam(player, team);
    }
}
