package ua.procamp.footballmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.PlayerMapper;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.exception.EntityNotFoundException;
import ua.procamp.footballmanager.repository.PlayerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static ua.procamp.footballmanager.TestUtils.generatePlayerWithIdAndNoTeam;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    private PlayerService service;

    @Mock
    private PlayerRepository repository;

    @BeforeEach
    void init() {
        service = new PlayerServiceImpl(repository);
    }

    @Test
    void findAllWhenRepoHasDataReturnsListOfPlayers() {
        List<Player> expected = List.of(generatePlayerWithIdAndNoTeam(1L), generatePlayerWithIdAndNoTeam(2L));
        when(repository.findAll()).thenReturn(expected);
        verifyNoMoreInteractions(repository);
        List<PlayerDto> players = service.findAll();
        List<Player> actual = PlayerMapper.listPlayerDtoToListPlayer(players);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void findAllWhenNoDataReturnsEmptyList() {
        List<Player> emptyList = Collections.emptyList();
        when(repository.findAll()).thenReturn(emptyList);
        verifyNoMoreInteractions(repository);
        List<PlayerDto> players = service.findAll();
        List<Player> actual = PlayerMapper.listPlayerDtoToListPlayer(players);
        assertThat(actual, equalTo(emptyList));
    }

    @Test
    void findByIdWhenPlayerExistsReturnsOptionalWithPlayer() {
        long playerId = 1L;
        Player expected = generatePlayerWithIdAndNoTeam(playerId);
        when(repository.findById(playerId)).thenReturn(Optional.of(expected));
        verifyNoMoreInteractions(repository);
        Player actual = service.findById(playerId)
                .map(PlayerMapper::playerDtoToPlayer)
                .orElseThrow();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void findByIdWhenPlayerNotExistsReturnsOptionalEmpty() {
        long notExistedId = 1L;
        when(repository.findById(notExistedId)).thenReturn(Optional.empty());
        verifyNoMoreInteractions(repository);
        Optional<PlayerDto> actual = service.findById(notExistedId);
        assertThat(actual, equalTo(Optional.empty()));
    }

    @Test
    void saveNotExistingPlayerReturnPlayerWithId() {
        Player playerWithoutId = generatePlayerWithIdAndNoTeam(-1);
        playerWithoutId.setId(null);
        Long generatedId = 1L;
        Player expected = generatePlayerWithIdAndNoTeam(generatedId);
        when(repository.save(playerWithoutId)).thenReturn(expected);
        verifyNoMoreInteractions(repository);
        PlayerDto playerDtoWithoutId = PlayerMapper.playerToPlayerDto(playerWithoutId);
        PlayerDto playerDtoWithId = service.save(playerDtoWithoutId);
        Player actual = PlayerMapper.playerDtoToPlayer(playerDtoWithId);
        assertThat(actual, equalTo(expected));
    }

    @Test
    void savePlayerWithIdThrowsIllegalStateException() {
        long playerId = 1L;
        Player playerWithId = generatePlayerWithIdAndNoTeam(playerId);
        PlayerDto playerDtoWithId = PlayerMapper.playerToPlayerDto(playerWithId);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.save(playerDtoWithId));
        String message = String.format("You're trying to save an existing player with id=%d..." +
                "If it is exactly what you want - pleas use update request", playerId);
        assertThat(exception.getMessage(), equalTo(message));
    }

    @Test
    void saveWhenPlayerDtoIsNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.save(null), "service.save(null) should throw NPE");
    }

    @Test
    void updateWhenPlayerExistsUpdatesExistingPlayer() {
        long playerId = 1L;
        Player existingPlayer = generatePlayerWithIdAndNoTeam(playerId);
        when(repository.findById(playerId)).thenReturn(Optional.of(existingPlayer));
        verifyNoMoreInteractions(repository);
        Player updatedPlayer = generatePlayerWithIdAndNoTeam(playerId);
        updatedPlayer.setFirstName("updated FirstName");
        PlayerDto updatedPlayerDto = PlayerMapper.playerToPlayerDto(updatedPlayer);
        service.update(updatedPlayerDto);
        assertThat(existingPlayer, samePropertyValuesAs(updatedPlayer));
    }

    @Test
    void updateWhenPlayerNotExistsThrowsEntityNotFoundException() {
        long notExistedId = 1L;
        when(repository.findById(notExistedId)).thenReturn(Optional.empty());
        verifyNoMoreInteractions(repository);
        Player player = generatePlayerWithIdAndNoTeam(notExistedId);
        PlayerDto playerDto = PlayerMapper.playerToPlayerDto(player);
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.update(playerDto));
        String message = String.format("Player with id %d not found", playerDto.getPlayerId());
        assertThat(message, equalTo(exception.getMessage()));
    }

    @Test
    void updateWhenPlayerDtoIsNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.update(null), "service.update(null) should throw NPE");
    }

    @Test
    void removeByIdInvokeRepositoryDeleteById() {
        long playerId = 1L;
        service.removeById(playerId);
        verify(repository, times(1)).deleteById(playerId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findPlayerByTeamWhenTeamWithPlayerExists() {
        Player player1 = generatePlayerWithIdAndNoTeam(1L);
        Player player2 = generatePlayerWithIdAndNoTeam(2L);
        Player player3 = generatePlayerWithIdAndNoTeam(3L);
        List<Player> players = List.of(player1, player2, player3);
        long teamId = 1L;
        when(repository.findByTeam(teamId)).thenReturn(players);
        List<PlayerDto> playersDto = service.findPlayersByTeam(teamId);
        List<Player> actual = PlayerMapper.listPlayerDtoToListPlayer(playersDto);
        assertThat(actual, containsInAnyOrder(players.toArray()));
    }
}