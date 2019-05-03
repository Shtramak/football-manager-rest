package ua.procamp.footballmanager.dto;

import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Position;
import ua.procamp.footballmanager.entity.Team;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlayerMapper {
    public static PlayerDto playerToPlayerDto(Player player) {
        String birthday = player.getBirthday()
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
        PlayerDto playerDto = PlayerDto.builder()
                .playerId(player.getId())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .position(player.getPosition().toString())
                .birthday(birthday)
                .build();
        Team playerTeam = player.getTeam();
        if (playerTeam != null) {
            playerDto.setTeamName(playerTeam.getName());
        }
        return playerDto;
    }

    public static Player playerDtoToPlayer(PlayerDto playerDto) {
        Player player = null;
        if (playerDto != null) {
            LocalDate birthday = LocalDate
                    .parse(playerDto.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            player = new Player();
            player.setId(playerDto.getPlayerId());
            player.setFirstName(playerDto.getFirstName());
            player.setLastName(playerDto.getLastName());
            player.setPosition(Position.valueOf(playerDto.getPosition()));
            player.setBirthday(birthday);
        }
        return player;
    }

    public static List<PlayerDto> listPlayerToListPlayerDto(List<Player> players) {
        return players.stream()
                .map(PlayerMapper::playerToPlayerDto)
                .collect(toList());
    }
}
