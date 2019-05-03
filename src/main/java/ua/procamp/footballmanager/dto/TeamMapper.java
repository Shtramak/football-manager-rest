package ua.procamp.footballmanager.dto;

import ua.procamp.footballmanager.entity.Team;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static ua.procamp.footballmanager.dto.PlayerMapper.playerDtoToPlayer;

public class TeamMapper {
    public static TeamDto teamToTeamDto(Team team) {
        TeamDto teamDto = TeamDto.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .build();
        if (team.getCaptain() != null) {
            teamDto.setCaptain(PlayerMapper.playerToPlayerDto(team.getCaptain()));
        }
        return teamDto;
    }

    public static Team teamDtoToTeam(TeamDto teamDtoDto) {
        Team team = new Team();
        team.setId(teamDtoDto.getTeamId());
        team.setName(teamDtoDto.getTeamName());
        team.setCaptain(playerDtoToPlayer(teamDtoDto.getCaptain()));
        return team;
    }

    public static List<TeamDto> listTeamToListTeamDto(List<Team> teams) {
        return teams.stream()
                .map(TeamMapper::teamToTeamDto)
                .collect(toList());
    }
}
