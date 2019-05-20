package ua.procamp.footballmanager.service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.procamp.footballmanager.dto.PlayerDto;
import ua.procamp.footballmanager.dto.PlayerMapper;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.repository.PlayerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static ua.procamp.footballmanager.TestUtils.generatePlayerWithId;

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
        List<Player> expected = List.of(generatePlayerWithId(1L), generatePlayerWithId(2L));
        when(repository.findAll()).thenReturn(expected);
        verifyNoMoreInteractions(repository);
        List<PlayerDto> players = service.findAll();
        List<Player> actual = PlayerMapper.listPlayerDtoToListPlayer(players);
        assertThat(actual, Matchers.containsInAnyOrder(expected.toArray()));
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
        Player expected = generatePlayerWithId(playerId);
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
        Long playerId = 1L;
        Player playerWithoutId = generatePlayerWithId(playerId);
        playerWithoutId.setId(null);
        Player expected = generatePlayerWithId(playerId);
        when(repository.save(playerWithoutId)).thenReturn(expected);
        PlayerDto playerDtoWithoutId = PlayerMapper.playerToPlayerDto(playerWithoutId);
        PlayerDto playerDtoWithId = service.save(playerDtoWithoutId);
        Player actual = PlayerMapper.playerDtoToPlayer(playerDtoWithId);
        assertThat(actual, equalTo(expected));
    }

    @Test
    void savePlayerWithIdThrowsIllegalStateException() {
        long playerId = 1L;
        Player playerWithId = generatePlayerWithId(playerId);
        PlayerDto playerDtoWithId = PlayerMapper.playerToPlayerDto(playerWithId);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.save(playerDtoWithId));
        String message = String.format("You're trying to save an existing player with id=%d..." +
                "If it is exactly what you want - pleas use update request", playerId);
        assertEquals(message, exception.getMessage());
    }
}