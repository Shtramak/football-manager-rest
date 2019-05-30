package ua.procamp.footballmanager.repository;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.config.JpaTestConfig;
import ua.procamp.footballmanager.entity.Team;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
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
        assertThat(actual, samePropertyValuesAs(expected));
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
        List<Team> teaamsBeforeRemoval = repository.findAll();
        Team removedTeam = teaamsBeforeRemoval.get(0);
        repository.deleteById(removedTeam.getId());
        List<Team> teamsAfterRemoval = repository.findAll();
        assertEquals(teaamsBeforeRemoval.size() - 1, teamsAfterRemoval.size());
    }
}
