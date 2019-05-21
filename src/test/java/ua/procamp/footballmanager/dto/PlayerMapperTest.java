package ua.procamp.footballmanager.dto;

import org.junit.jupiter.api.Test;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.procamp.footballmanager.TestUtils.generatePlayerWithIdAndNoTeam;
import static ua.procamp.footballmanager.TestUtils.mapPlayerFieldsOnPlayerDtoFields;

class PlayerMapperTest {

    @Test
    void playerToPlayerDtoWithoutTeamReturnsPlayerDtoWithNullTeamName() {
        Player player = generatePlayerWithIdAndNoTeam(1);
        PlayerDto playerDto = new PlayerDto();
        mapPlayerFieldsOnPlayerDtoFields(player, playerDto);
        PlayerDto actual = PlayerMapper.playerToPlayerDto(player);
        PlayerMapper.playerToPlayerDto(player);
        assertThat(playerDto, samePropertyValuesAs(actual));
    }

    @Test
    void playerToPlayerDtoWithoutTeamReturnsPlayerDtoWithTeamName() {
        Player player = generatePlayerWithIdAndNoTeam(1);
        Team team = new Team();
        team.setName("testTeam");
        player.setTeam(team);
        PlayerDto playerDto = new PlayerDto();
        mapPlayerFieldsOnPlayerDtoFields(player, playerDto);
        PlayerDto actual = PlayerMapper.playerToPlayerDto(player);
        PlayerMapper.playerToPlayerDto(player);
        assertThat(playerDto, samePropertyValuesAs(actual));
    }

    @Test
    void playerDtoToPlayerWithoutTeam() {
        Player player = generatePlayerWithIdAndNoTeam(1);
        PlayerDto playerDto = new PlayerDto();
        mapPlayerFieldsOnPlayerDtoFields(player, playerDto);
        Player actual = PlayerMapper.playerDtoToPlayer(playerDto);
        assertThat(player, samePropertyValuesAs(actual));
    }

    @Test
    void listPlayerToListPlayerDto() {
        Player player1 = generatePlayerWithIdAndNoTeam(1);
        Player player2 = generatePlayerWithIdAndNoTeam(2);
        Player player3 = generatePlayerWithIdAndNoTeam(3);
        List<Player> players = List.of(player1, player2, player3);
        PlayerDto playerDto1 = new PlayerDto();
        PlayerDto playerDto2 = new PlayerDto();
        PlayerDto playerDto3 = new PlayerDto();
        mapPlayerFieldsOnPlayerDtoFields(player1, playerDto1);
        mapPlayerFieldsOnPlayerDtoFields(player2, playerDto2);
        mapPlayerFieldsOnPlayerDtoFields(player3, playerDto3);
        List<PlayerDto> playersDto = List.of(playerDto1, playerDto2, playerDto3);
        List<PlayerDto> actual = PlayerMapper.listPlayerToListPlayerDto(players);
        assertEquals(playersDto.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertThat(playersDto.get(i), samePropertyValuesAs(actual.get(i)));
        }
    }

    @Test
    void listPlayerDtoToListPlayer() {
        Player player1 = generatePlayerWithIdAndNoTeam(1);
        Player player2 = generatePlayerWithIdAndNoTeam(2);
        Player player3 = generatePlayerWithIdAndNoTeam(3);
        List<Player> players = List.of(player1, player2, player3);
        PlayerDto playerDto1 = new PlayerDto();
        PlayerDto playerDto2 = new PlayerDto();
        PlayerDto playerDto3 = new PlayerDto();
        mapPlayerFieldsOnPlayerDtoFields(player1, playerDto1);
        mapPlayerFieldsOnPlayerDtoFields(player2, playerDto2);
        mapPlayerFieldsOnPlayerDtoFields(player3, playerDto3);
        List<PlayerDto> playersDto = List.of(playerDto1, playerDto2, playerDto3);
        List<Player> actual = PlayerMapper.listPlayerDtoToListPlayer(playersDto);
        assertEquals(playersDto.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertThat(players.get(i), samePropertyValuesAs(actual.get(i)));
        }
    }
}