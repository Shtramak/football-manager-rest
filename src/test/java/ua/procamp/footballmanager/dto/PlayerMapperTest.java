package ua.procamp.footballmanager.dto;

import org.junit.jupiter.api.Test;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.procamp.footballmanager.TestUtils.generatePlayerWithId;

class PlayerMapperTest {

    @Test
    void playerToPlayerDtoWithoutTeamReturnsPlayerDtoWithNullTeamName() {
        Player player = generatePlayerWithId(1L);
        PlayerDto playerDto = new PlayerDto();
        setPlayerFeildsToPlayerDtoFields(player, playerDto);
        PlayerDto actual = PlayerMapper.playerToPlayerDto(player);
        PlayerMapper.playerToPlayerDto(player);
        assertThat(playerDto, samePropertyValuesAs(actual));
    }

    @Test
    void playerToPlayerDtoWithoutTeamReturnsPlayerDtoWithTeamName() {
        Player player = generatePlayerWithId(1L);
        Team team = new Team();
        team.setName("testTeam");
        player.setTeam(team);
        PlayerDto playerDto = new PlayerDto();
        setPlayerFeildsToPlayerDtoFields(player, playerDto);
        PlayerDto actual = PlayerMapper.playerToPlayerDto(player);
        PlayerMapper.playerToPlayerDto(player);
        assertThat(playerDto, samePropertyValuesAs(actual));
    }

    @Test
    void playerDtoToPlayerWithoutTeam() {
        Player player = generatePlayerWithId(1L);
        PlayerDto playerDto = new PlayerDto();
        setPlayerFeildsToPlayerDtoFields(player, playerDto);
        Player actual = PlayerMapper.playerDtoToPlayer(playerDto);
        assertThat(player, samePropertyValuesAs(actual));
    }

    @Test
    void listPlayerToListPlayerDto() {
        Player player1 = generatePlayerWithId(1L);
        Player player2 = generatePlayerWithId(2L);
        Player player3 = generatePlayerWithId(3L);
        List<Player> players = List.of(player1, player2, player3);
        PlayerDto playerDto1 = new PlayerDto();
        PlayerDto playerDto2 = new PlayerDto();
        PlayerDto playerDto3 = new PlayerDto();
        setPlayerFeildsToPlayerDtoFields(player1, playerDto1);
        setPlayerFeildsToPlayerDtoFields(player2, playerDto2);
        setPlayerFeildsToPlayerDtoFields(player3, playerDto3);
        List<PlayerDto> playersDto = List.of(playerDto1, playerDto2, playerDto3);
        List<PlayerDto> actual = PlayerMapper.listPlayerToListPlayerDto(players);
        assertEquals(playersDto.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertThat(playersDto.get(i),samePropertyValuesAs(actual.get(i)));
        }
    }

    @Test
    void listPlayerDtoToListPlayer() {
        Player player1 = generatePlayerWithId(1L);
        Player player2 = generatePlayerWithId(2L);
        Player player3 = generatePlayerWithId(3L);
        List<Player> players = List.of(player1, player2, player3);
        PlayerDto playerDto1 = new PlayerDto();
        PlayerDto playerDto2 = new PlayerDto();
        PlayerDto playerDto3 = new PlayerDto();
        setPlayerFeildsToPlayerDtoFields(player1, playerDto1);
        setPlayerFeildsToPlayerDtoFields(player2, playerDto2);
        setPlayerFeildsToPlayerDtoFields(player3, playerDto3);
        List<PlayerDto> playersDto = List.of(playerDto1, playerDto2, playerDto3);
        List<Player> actual = PlayerMapper.listPlayerDtoToListPlayer(playersDto);
        assertEquals(playersDto.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertThat(players.get(i),samePropertyValuesAs(actual.get(i)));
        }
    }

    private void setPlayerFeildsToPlayerDtoFields(Player player, PlayerDto playerDto) {
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
}