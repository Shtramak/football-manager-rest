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
import ua.procamp.footballmanager.entity.Position;
import ua.procamp.footballmanager.repository.PlayerRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
        List<PlayerDto> players = service.findAll();
        List<Player> actual = PlayerMapper.listPlayerDtoToListPlayer(players);
        assertThat(actual, Matchers.equalTo(emptyList));
    }

    private Player generatePlayerWithId(Long id) {
        Player player = new Player();
        player.setId(id);
        player.setFirstName("FirstName-" + id);
        player.setLastName("LastName-" + id);
        player.setBirthday(LocalDate.now());
        player.setPosition(Position.DEFENDER);
        return player;
    }
}