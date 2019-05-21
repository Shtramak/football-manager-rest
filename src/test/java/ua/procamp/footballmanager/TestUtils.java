package ua.procamp.footballmanager;

import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.PlayerMapper;
import ua.procamp.footballmanager.dto.TeamDto;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Position;
import ua.procamp.footballmanager.entity.Team;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtils {
    public static Player generatePlayerWithIdAndNoTeam(long id) {
        Player player = new Player();
        player.setId(id);
        player.setFirstName("FirstName-" + id);
        player.setLastName("LastName-" + id);
        player.setBirthday(LocalDate.now());
        player.setPosition(Position.DEFENDER);
        return player;
    }

    public static Team generateTeamWithId(long id) {
        Team team = new Team();
        team.setId(id);
        team.setName("Team-" + id);
        return team;
    }

    public static void mapPlayerFieldsOnPlayerDtoFields(Player player, PlayerDto playerDto) {
        playerDto.setPlayerId(player.getId());
        playerDto.setFirstName(player.getFirstName());
        playerDto.setLastName(player.getLastName());
        playerDto.setPosition(player.getPosition().toString());
        String birthday = player.getBirthday()
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
        playerDto.setBirthday(birthday);
        if (player.getTeam() != null) {
            playerDto.setTeamName(player.getTeam().getName());
        }
    }

    public static void mapTeamFieldsOnTeamDtoFields(Team team, TeamDto teamDto) {
        teamDto.setTeamId(team.getId());
        teamDto.setTeamName(team.getName());
        Player captain = team.getCaptain();
        if (captain != null) {
            PlayerDto captainDto = PlayerMapper.playerToPlayerDto(captain);
            teamDto.setCaptain(captainDto);
        }
    }

}
