package ua.procamp.footballmanager.service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.procamp.footballmanager.dto.TeamDto;
import ua.procamp.footballmanager.dto.TeamMapper;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;
import ua.procamp.footballmanager.exception.EntityNotFoundException;
import ua.procamp.footballmanager.repository.TeamRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static ua.procamp.footballmanager.TestUtils.generateTeamWithIdAndNoCaptain;
import static ua.procamp.footballmanager.TestUtils.generateTeamWithIdAndPlayers;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {
    @Mock
    private TeamRepository repository;
    private TeamService service;

    @BeforeEach
    void setUp() {
        service = new TeamServiceImpl(repository);
    }

    @Test
    void findAllWhenRepoHasDataReturnsListOfTeams() {
        Team team1 = generateTeamWithIdAndNoCaptain(1);
        Team team2 = generateTeamWithIdAndNoCaptain(2);
        Team team3 = generateTeamWithIdAndNoCaptain(3);
        List<Team> teams = List.of(team1, team2, team3);
        when(repository.findAll()).thenReturn(teams);
        verifyNoMoreInteractions(repository);
        List<TeamDto> teamsDto = service.findAll();
        assertThat(teamsDto.size(), Matchers.equalTo(teams.size()));
        for (int i = 0; i < teams.size(); i++) {
            TeamDto teamDto = TeamMapper.teamToTeamDto(teams.get(i));
            assertThat(teamsDto.get(i), samePropertyValuesAs(teamDto));
        }
    }

    @Test
    void findAllWhenRepoHasNoDataReturnsEmptyList() {
        List<Team> emptyList = Collections.emptyList();
        when(repository.findAll()).thenReturn(emptyList);
        List<TeamDto> teamsDto = service.findAll();
        assertThat(teamsDto, Matchers.equalTo(emptyList));
    }

    @Test
    void findByIdWhenTeamExistsReturnsOptionalWithTeam() {
        long teamId = 1;
        Team team = generateTeamWithIdAndNoCaptain(teamId);
        when(repository.findById(teamId)).thenReturn(Optional.of(team));
        Mockito.verifyNoMoreInteractions(repository);
        TeamDto teamDto = TeamMapper.teamToTeamDto(team);
        TeamDto actual = service.findById(teamId).orElseThrow();
        assertThat(actual, samePropertyValuesAs(teamDto));
    }

    @Test
    void findByIdWhenTeamNotExistsReturnsOptionalEmpty() {
        long notExistedId = -1L;
        when(repository.findById(notExistedId)).thenReturn(Optional.empty());
        Optional<TeamDto> actual = service.findById(notExistedId);
        assertThat(actual, equalTo(Optional.empty()));
    }

    @Test
    void saveNotExistingTeamReturnTeamWithId() {
        Team teamWithoutId = generateTeamWithIdAndNoCaptain(-1);
        teamWithoutId.setId(null);
        long generatedId = 1;
        Team expected = generateTeamWithIdAndNoCaptain(generatedId);
        when(repository.save(teamWithoutId)).thenReturn(expected);
        verifyZeroInteractions(repository);
        TeamDto teamWithoutIdDto = TeamMapper.teamToTeamDto(teamWithoutId);
        TeamDto actual = service.save(teamWithoutIdDto);
        TeamDto expectedDto = TeamMapper.teamToTeamDto(expected);
        assertThat(actual, samePropertyValuesAs(expectedDto));
    }

    @Test
    void saveTeamWithIdThrowsIllegalStateException() {
        long teamId = 1L;
        Team teamWithId = generateTeamWithIdAndNoCaptain(teamId);
        TeamDto teamWithIdDto = TeamMapper.teamToTeamDto(teamWithId);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.save(teamWithIdDto));
        String message = String.format("You're trying to save an existing team with id=%d...", teamId);
        assertThat(exception.getMessage(), equalTo(message));
    }

    @Test
    void saveWhenTeamDtoIsNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.save(null), "service.save(null) should throw NPE");
    }

    @Test
    void removeByIdWhenTeamExists() {
        long teamId = 1;
        Team team = generateTeamWithIdAndPlayers(teamId, 3);
        Set<Player> teamPlayers = team.getPlayers();
        when(repository.findById(teamId)).thenReturn(Optional.of(team));
        service.removeById(teamId);
        verify(repository, times(1)).deleteById(teamId);
        verifyNoMoreInteractions(repository);
        Assertions.assertTrue(teamPlayers.stream()
                .map(Player::getTeam)
                .allMatch(Objects::isNull)
        );
    }

    @Test
    void removeByIdWhenTeamNotExist() {
        long notExistedId = -1L;
        when(repository.findById(notExistedId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.removeById(notExistedId));
        verifyNoMoreInteractions(repository);
        String message = String.format("No team with id %d found", notExistedId);
        assertThat(exception.getMessage(), equalTo(message));
    }

}