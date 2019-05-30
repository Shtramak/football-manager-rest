package ua.procamp.footballmanager.repository;

import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Position;
import ua.procamp.footballmanager.entity.Team;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RepositoryTestUtils {

    public static List<Team> teamsWithPlayersAndCaptainsFromDefaultDataSetScript() {
        Team team1 = teamWithPlayers(1, "Dynamo");
        Player team1Captain = team1.getPlayers()
                .stream()
                .filter(player -> player.getId() == 3)
                .findAny()
                .orElseThrow();
        team1.setCaptain(team1Captain);
        Team team2 = teamWithPlayers(2, "Juventus");
        return List.of(team1, team2);
    }

    private static Set<Player> playersFromDefaultDataSetScript() {
        Team team1 = new Team();
        team1.setId(1L);
        Player p1 = player(1, "Denys", "Boiko", LocalDate.of(1988, 1, 29), Position.GOALKEEPER, team1);
        Player p2 = player(2, "Mykyta", "Burda", LocalDate.of(1995, 3, 24), Position.DEFENDER, team1);
        Player p3 = player(3, "Viktor", "Tsyhankov", LocalDate.of(1988, 11, 15), Position.MIDFILDER, team1);
        Player p4 = player(4, "Artem", "Besedin", LocalDate.of(1988, 11, 15), Position.FORWARD, team1);
        Team team2 = new Team();
        team2.setId(2L);
        Player p5 = player(5, "Wojciech", "Boiko", LocalDate.of(1988, 1, 29), Position.GOALKEEPER, team2);
        Player p6 = player(6, "Giorgio", "Burda", LocalDate.of(1995, 3, 24), Position.DEFENDER, team2);
        Player p7 = player(7, "Cristiano", "Ronaldo", LocalDate.of(1985, 2, 5), Position.FORWARD, team2);
        Player p8 = player(8, "Sami", "Tsyhankov", LocalDate.of(1988, 11, 15), Position.MIDFILDER, team2);
        return Set.of(p1, p2, p3, p4, p5, p6, p7, p8);
    }

    private static Player player(long playerId,
                                 String firstName,
                                 String lastName,
                                 LocalDate birthday,
                                 Position position,
                                 Team team) {

        Player player = new Player();
        player.setId(playerId);
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setBirthday(birthday);
        player.setPosition(position);
        player.setTeam(team);
        return player;
    }

    private static Team teamWithPlayers(long teamId, String teamName) {
        Team team = new Team();
        team.setId(teamId);
        team.setName(teamName);
        Class<? extends Team> teamClass = team.getClass();
        try {
            Method setPlayers = teamClass.getDeclaredMethod("setPlayers", Set.class);
            setPlayers.setAccessible(true);
            Set<Player> teamPlayers = playersFromDefaultDataSetScript().stream()
                    .filter(player -> player.getTeam().getId() == teamId)
                    .collect(Collectors.toSet());
            teamPlayers.forEach(player -> player.setTeam(team));
            setPlayers.invoke(team, teamPlayers);
            return team;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
