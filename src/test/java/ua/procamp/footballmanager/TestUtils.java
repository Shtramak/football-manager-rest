package ua.procamp.footballmanager;

import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.PlayerMapper;
import ua.procamp.footballmanager.dto.TeamDto;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Position;
import ua.procamp.footballmanager.entity.Team;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

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

    public static Team generateTeamWithIdAndNoCaptain(long id) {
        Team team = new Team();
        team.setId(id);
        team.setName("Team-" + id);
        return team;
    }

    public static Team generateTeamWithIdAndPlayers(long id, int numberOfPlayers) {
        Team team = generateTeamWithIdAndNoCaptain(id);
        Class<? extends Team> teamClass = team.getClass();
        try {
            Method setPlayers = teamClass.getDeclaredMethod("setPlayers", Set.class);
            setPlayers.setAccessible(true);
            setPlayers.invoke(team, setOfPlayers(team, numberOfPlayers));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return team;
    }

    private static Set<Player> setOfPlayers(Team team, int number) {
        Set<Player> players = new HashSet<>();
        for (int i = 0; i < number; i++) {
            Player player = generatePlayerWithIdAndNoTeam(i);
            player.setTeam(team);
            players.add(player);
        }
        return players;
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
