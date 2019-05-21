package ua.procamp.footballmanager.dto;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ua.procamp.footballmanager.entity.Player;
import ua.procamp.footballmanager.entity.Team;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static ua.procamp.footballmanager.TestUtils.generatePlayerWithIdAndNoTeam;
import static ua.procamp.footballmanager.TestUtils.generateTeamWithId;
import static ua.procamp.footballmanager.TestUtils.mapTeamFieldsOnTeamDtoFields;

class TeamMapperTest {
    @Test
    void teamToTeamDtoWithoutCaptainReturnsTeamDtoWithNullCaptain() {
        Team team = generateTeamWithId(1);
        TeamDto teamDto = new TeamDto();
        mapTeamFieldsOnTeamDtoFields(team, teamDto);
        TeamDto actual = TeamMapper.teamToTeamDto(team);
        assertThat(actual, samePropertyValuesAs(teamDto));
    }

    @Test
    void teamToTeamDtoWithCaptainReturnsTeamDtoWithCaptainDto() {
        Team team = generateTeamWithId(1);
        Player captain = generatePlayerWithIdAndNoTeam(1);
        team.setCaptain(captain);
        TeamDto teamDto = new TeamDto();
        mapTeamFieldsOnTeamDtoFields(team, teamDto);
        TeamDto actual = TeamMapper.teamToTeamDto(team);
        assertThat(actual.getCaptain(), Matchers.samePropertyValuesAs(teamDto.getCaptain()));
    }

}