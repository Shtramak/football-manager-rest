package ua.procamp.footballmanager.repository;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.TestUtils;
import ua.procamp.footballmanager.config.JpaTestConfig;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ua.procamp.footballmanager.repository.RepositoryTestUtils.teamsWithPlayersAndCaptainsFromDefaultDataSetScript;

@SpringJUnitConfig(JpaTestConfig.class)
@Transactional
public class TeamRepositoryTest {
    @Autowired
    private TeamRepository repository;

    @Test
    @Sql(scripts = "/sql-scripts/dataset.sql")
    void findAllWhenDataExistsReturnsListOfTeams() {
        List<Team> expected = teamsWithPlayersAndCaptainsFromDefaultDataSetScript();
        List<Team> actual = repository.findAll();
        assertEquals(expected.size(), actual.size());
        for (Team actualTeam : actual) {
            Team expectedTeam = expected.stream()
                    .filter(team -> team.getId().equals(actualTeam.getId()))
                    .findAny()
                    .orElseThrow();
            assertEquals(expectedTeam.getName(), actualTeam.getName());
            assertThat(actualTeam.getPlayers(), Matchers.containsInAnyOrder(actualTeam.getPlayers().toArray()));
        }
    }

    @Test
    void findAllWhenNoDataReturnsEmptyList() {
        List<Team> actual = repository.findAll();
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    @Sql(scripts = "/sql-scripts/dataset.sql")
    void findByIdWhenTeamExistsReturnsOptionalWithTeam() {
        List<Team> teams = teamsWithPlayersAndCaptainsFromDefaultDataSetScript();
        Team expected = teams.get(0);
        Team actual = repository.findById(expected.getId()).orElseThrow();
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getId(), expected.getId());
    }

    @Test
    @Sql(scripts = "/sql-scripts/dataset.sql")
    void findByIdWhenTeamNotExistsReturnsOptionalEmpty() {
        Optional<Team> actual = repository.findById(-1L);
        assertEquals(actual, Optional.empty());
    }

    @Test
    void saveNewTeamInsertsTeamToDatabaseWithNewId() {
        Team newTeam = new Team();
        assertNull(newTeam.getId());
        Team persistedTeam = repository.save(newTeam);
        assertNotNull(persistedTeam.getId());
    }

    @Test
    @Sql(scripts = "/sql-scripts/dataset.sql")
    void deleteByIdWhenIdExistsDeletesEntryWithPassedIdFromDatabase() {
        List<Team> teamsBeforeRemoval = repository.findAll();
        Team removedTeam = teamsBeforeRemoval.get(0);
        repository.deleteById(removedTeam.getId());
        List<Team> teamsAfterRemoval = repository.findAll();
        assertEquals(teamsBeforeRemoval.size() - 1, teamsAfterRemoval.size());
    }

    @Test
    void deleteWhenIdNotExistsDoesNothig() {
        repository.deleteById(-1L);
    }

    @Test
    @Sql(scripts = "/sql-scripts/dataset.sql")
    void deleteByIdWhenAllTeamsToBeDeletedWorksFine() {
        List<Team> teamsBeforeRemoval = repository.findAll();
        teamsBeforeRemoval.forEach(team -> repository.deleteById(team.getId()));
        List<Team> teamsAfterRemoval = repository.findAll();
        assertEquals(Collections.emptyList(), teamsAfterRemoval);
    }

    @Test
    @Sql(scripts = "/sql-scripts/dataset.sql")
    void findCaptainWhenCaptainExistsReturnsOptionalWithCaptainPlayer() {
        List<Team> teams = repository.findAll();
        Team teamWithCaptain = teams.stream()
                .filter(team -> team.getCaptain() != null)
                .findAny()
                .orElseThrow();
        Player expectedCaptain = teamWithCaptain.getCaptain();
        Player actualCaptain = repository.findCaptain(teamWithCaptain.getId()).orElseThrow();
        assertThat(actualCaptain, samePropertyValuesAs(expectedCaptain));
    }

    @Test
    @Sql(scripts = "/sql-scripts/dataset.sql")
    void findCaptainWhenCaptainNotExistsReturnsOptionalEmpty() {
        List<Team> teams = repository.findAll();
        Team teamWithCaptain = teams.stream()
                .filter(team -> team.getCaptain() == null)
                .findAny()
                .orElseThrow();
        Optional<Player> actualCaptain = repository.findCaptain(teamWithCaptain.getId());
        assertEquals(actualCaptain, Optional.empty());
    }

    @Test
    @Sql(scripts = "/sql-scripts/dataset.sql")
    void addNewPlayerWithoutIdToTeamAddsHimToDatabase() {
        List<Team> teams = repository.findAll();
        Team team = teams.get(0);
        Long teamId = team.getId();
        Player newPlayer = TestUtils.generatePlayerWithIdAndNoTeam(1L);
        newPlayer.setId(null);
        int numOfPlayersBefore = team.getPlayers().size();
        repository.addNewPlayerToTeam(teamId, newPlayer);
        repository.flush();
        assertNotNull(newPlayer.getId());
        int numOfPlayersAfter = team.getPlayers().size();
        assertEquals(numOfPlayersBefore + 1, numOfPlayersAfter);
        assertThat(team.getPlayers(), hasItem(newPlayer));
    }

    @Test
    @Sql(scripts = "/sql-scripts/dataset.sql")
    void addPlayerWithExistingIdToTeamThrowsSomething() {
        throw new UnsupportedOperationException();
    }
}
